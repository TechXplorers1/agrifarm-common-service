package com.agrifarms.common.service;

import com.agrifarms.common.entity.Booking;
import com.agrifarms.common.entity.User;
import com.agrifarms.common.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final NotificationService notificationService;
    private final UserService userService;

    public Booking createBooking(Booking booking) {
        booking.setBookingDate(LocalDateTime.now());
        booking.setStatus("PENDING");
        Booking savedBooking = bookingRepository.save(booking);

        // Notify the Provider (Asset Owner) about the new booking request
        Optional<User> providerOpt = userService.getUserById(booking.getProviderId());
        if (providerOpt.isPresent() && providerOpt.get().getFcmToken() != null) {
            String fcmToken = providerOpt.get().getFcmToken();
            String title = "New Booking Request";
            String body = "You have a new request for your " + 
                          (booking.getAssetType() != null ? booking.getAssetType() : "asset") + ".";
            
            Map<String, String> data = new HashMap<>();
            data.put("bookingId", savedBooking.getBookingId());
            data.put("type", "booking_request");

            notificationService.sendPushNotification(fcmToken, title, body, data);
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
        if (farmerOpt.isPresent() && farmerOpt.get().getFcmToken() != null) {
            String fcmToken = farmerOpt.get().getFcmToken();
            String title = "Booking Update";
            String body = "Your booking request for " + 
                          (booking.getAssetType() != null ? booking.getAssetType() : "an asset") + 
                          " has been " + status + ".";
            
            Map<String, String> data = new HashMap<>();
            data.put("bookingId", updatedBooking.getBookingId());
            data.put("status", status);
            data.put("type", "booking_status_update");

            notificationService.sendPushNotification(fcmToken, title, body, data);
        }

        return updatedBooking;
    }
}
