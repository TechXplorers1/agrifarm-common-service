package com.agrifarms.common.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.agrifarms.common.entity.Booking;
import com.agrifarms.common.entity.User;
import com.agrifarms.common.repository.BookingRepository;
import com.agrifarms.common.repository.EquipmentRepository;
import com.agrifarms.common.repository.ServiceOfferingRepository;
import com.agrifarms.common.repository.TransportVehicleRepository;
import com.agrifarms.common.repository.WorkerGroupRepository;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final NotificationService notificationService;
    private final UserService userService;
    private final EquipmentRepository equipmentRepository;
    private final ServiceOfferingRepository serviceRepository;
    private final TransportVehicleRepository transportRepository;
    private final WorkerGroupRepository workerRepository;

    public BookingService(BookingRepository bookingRepository, NotificationService notificationService, UserService userService,
            EquipmentRepository equipmentRepository, ServiceOfferingRepository serviceRepository,
            TransportVehicleRepository transportRepository, WorkerGroupRepository workerRepository) {
        this.bookingRepository = bookingRepository;
        this.notificationService = notificationService;
        this.userService = userService;
        this.equipmentRepository = equipmentRepository;
        this.serviceRepository = serviceRepository;
        this.transportRepository = transportRepository;
        this.workerRepository = workerRepository;
    }

    private String getAssetName(String assetType, String assetId) {
        if (assetType == null || assetId == null) {
            return "asset";
        }

        try {
            switch (assetType.toLowerCase()) {
                case "equipment":
                    return equipmentRepository.findById(assetId)
                            .map(e -> {
                                String type = e.getCategory() != null ? e.getCategory() : "Equipment";
                                if (e.getBrandModel() != null && !e.getBrandModel().trim().isEmpty()) {
                                    return type + " - " + e.getBrandModel();
                                }
                                return type;
                            })
                            .orElse("equipment");
                case "service":
                    return serviceRepository.findById(assetId)
                            .map(s -> {
                                String sType = s.getServiceType() != null ? s.getServiceType() : "Service";
                                if (s.getBusinessName() != null && !s.getBusinessName().trim().isEmpty()) {
                                    return sType + " - " + s.getBusinessName();
                                }
                                return sType;
                            })
                            .orElse("service");
                case "transport":
                    return transportRepository.findById(assetId)
                            .map(t -> {
                                String vType = t.getVehicleType() != null ? t.getVehicleType() : "Transport";
                                if (t.getVehicleNumber() != null && !t.getVehicleNumber().trim().isEmpty()) {
                                    return vType + " (" + t.getVehicleNumber() + ")";
                                }
                                return vType;
                            })
                            .orElse("transport vehicle");
                case "worker_group":
                case "farm_workers":
                case "worker":
                    return workerRepository.findById(assetId)
                            .map(w -> w.getGroupName() != null ? w.getGroupName() : "farm workers")
                            .orElse("worker group");
                default:
                    return assetType;
            }
        } catch (Exception e) {
            return assetType;
        }
    }

    public Booking createBooking(Booking booking) {
        booking.setBookingDate(LocalDateTime.now());
        booking.setStatus("PENDING");
        Booking savedBooking = bookingRepository.save(booking);

        // Notify the Provider (Asset Owner) about the new booking request
        Optional<User> providerOpt = userService.getUserById(booking.getProviderId());
        Optional<User> requesterOpt = userService.getUserById(booking.getFarmerId());
        if (providerOpt.isPresent()) {
            String fcmToken = providerOpt.get().getFcmToken();
            String assetName = getAssetName(booking.getAssetType(), booking.getAssetId());
            String requesterName = requesterOpt.map(User::getFullName).orElse("Someone");

            String title = "New Booking Request";
            String body = requesterName + " requested to book your " + assetName + "!";

            Map<String, String> data = new HashMap<>();
            data.put("bookingId", savedBooking.getBookingId());
            data.put("type", "booking_request");

            notificationService.saveAndSendNotification(booking.getProviderId(), fcmToken, title, body, "booking_request", savedBooking.getBookingId(), data);
        }

        notificationService.notifyAdmin("New booking submitted", "Booking " + savedBooking.getBookingId() + " has been created", "warning", savedBooking.getBookingId());

        return savedBooking;
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public List<Booking> getBookingsByFarmer(String farmerId) {
        return bookingRepository.findByFarmerId(farmerId);
    }

    public List<Booking> getBookingsByProvider(String providerId) {
        return bookingRepository.findByProviderId(providerId);
    }

    public List<Booking> getBookingsByAsset(String assetId) {
        return bookingRepository.findByAssetId(assetId);
    }

    public Booking updateBookingStatus(String bookingId, String status) {
        return updateBookingStatus(bookingId, status, null, null);
    }

    public Booking updateBookingStatus(String bookingId, String status, String cancelledBy, String cancellationReason) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        booking.setStatus(status);
        if (cancelledBy != null) {
            booking.setCancelledBy(cancelledBy);
        }
        if (cancellationReason != null) {
            booking.setCancellationReason(cancellationReason);
        }
        Booking updatedBooking = bookingRepository.save(booking);

        // Notify appropriate party
        Optional<User> farmerOpt = userService.getUserById(booking.getFarmerId());
        Optional<User> providerOpt = userService.getUserById(booking.getProviderId());

        if ("CANCELLED".equalsIgnoreCase(status)) {
            // If cancelled, notify the provider (since it was initiated by the farmer)
            if (providerOpt.isPresent()) {
                String fcmToken = providerOpt.get().getFcmToken();
                String assetName = getAssetName(booking.getAssetType(), booking.getAssetId());
                String farmerName = farmerOpt.map(User::getFullName).orElse("A farmer");

                String title = "Booking Cancelled";
                String body = farmerName + " has cancelled their booking for " + assetName + 
                        (cancellationReason != null && !cancellationReason.trim().isEmpty() ? " due to: " + cancellationReason : ".");

                Map<String, String> data = new HashMap<>();
                data.put("bookingId", updatedBooking.getBookingId());
                data.put("status", status);
                data.put("type", "booking_cancelled");

                notificationService.saveAndSendNotification(booking.getProviderId(), fcmToken, title, body, "booking_cancelled", updatedBooking.getBookingId(), data);
            }
            notificationService.notifyAdmin("Booking cancelled", "Booking " + updatedBooking.getBookingId() + " was cancelled by " + (cancelledBy != null ? cancelledBy : "user"), "destructive", updatedBooking.getBookingId());
        } else {
            // Notify the Farmer (Requester) about the status change
            if (farmerOpt.isPresent()) {
                String fcmToken = farmerOpt.get().getFcmToken();
                String assetName = getAssetName(booking.getAssetType(), booking.getAssetId());
                String providerName = providerOpt.map(User::getFullName).orElse("The provider");

                String title = "Booking " + status;
                String body = providerName + " has " + status.toLowerCase() + " your request for " + assetName + ".";

                Map<String, String> data = new HashMap<>();
                data.put("bookingId", updatedBooking.getBookingId());
                data.put("status", status);
                data.put("type", "booking_status_update");

                notificationService.saveAndSendNotification(booking.getFarmerId(), fcmToken, title, body, "booking_status_update", updatedBooking.getBookingId(), data);
            }

            if ("COMPLETED".equalsIgnoreCase(status) || "CONFIRMED".equalsIgnoreCase(status) || "REJECTED".equalsIgnoreCase(status)) {
                String type = "COMPLETED".equalsIgnoreCase(status) || "CONFIRMED".equalsIgnoreCase(status) ? "success" : "destructive";
                notificationService.notifyAdmin("Booking " + status.toLowerCase(), "Booking " + updatedBooking.getBookingId() + " is now " + status.toLowerCase(), type, updatedBooking.getBookingId());
            }
        }

        return updatedBooking;
    }
}
