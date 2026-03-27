package com.agrifarms.common.service;

import com.agrifarms.common.entity.Booking;
import com.agrifarms.common.entity.User;
import com.agrifarms.common.repository.BookingRepository;
import com.agrifarms.common.repository.EquipmentRepository;
import com.agrifarms.common.repository.ServiceOfferingRepository;
import com.agrifarms.common.repository.TransportVehicleRepository;
import com.agrifarms.common.repository.WorkerGroupRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
        if (assetType == null || assetId == null) return "asset";
        
        try {
            switch (assetType.toLowerCase()) {
                case "equipment":
                    return equipmentRepository.findById(assetId)
                            .map(e -> e.getBrandModel() != null ? e.getBrandModel() : e.getCategory())
                            .orElse("equipment");
                case "service":
                    return serviceRepository.findById(assetId)
                            .map(s -> s.getBusinessName() != null ? s.getBusinessName() : s.getServiceType())
                            .orElse("service");
                case "transport":
                    return transportRepository.findById(assetId)
                            .map(t -> t.getVehicleType())
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
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        booking.setStatus(status);
        Booking updatedBooking = bookingRepository.save(booking);

        // Notify the Farmer (Requester) about the status change
        Optional<User> farmerOpt = userService.getUserById(booking.getFarmerId());
        Optional<User> providerOpt = userService.getUserById(booking.getProviderId());
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

        return updatedBooking;
    }
}
