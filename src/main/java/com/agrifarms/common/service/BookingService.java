package com.agrifarms.common.service;

import com.agrifarms.common.entity.Booking;
import com.agrifarms.common.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;

    public Booking createBooking(Booking booking) {
        booking.setBookingDate(LocalDateTime.now());
        booking.setStatus("PENDING");
        return bookingRepository.save(booking);
    }

    public List<Booking> getBookingsByFarmer(String farmerId) {
        return bookingRepository.findByFarmerId(farmerId);
    }

    public List<Booking> getBookingsByProvider(String providerId) {
        return bookingRepository.findByProviderId(providerId);
    }

    public Booking updateBookingStatus(String bookingId, String status) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        booking.setStatus(status);
        return bookingRepository.save(booking);
    }
}
