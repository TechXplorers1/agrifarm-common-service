package com.agrifarms.common.controller;

import com.agrifarms.common.dto.BookingDTO;
import com.agrifarms.common.dto.DtoMapper;
import com.agrifarms.common.entity.Booking;
import com.agrifarms.common.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class BookingController {

    private final BookingService bookingService;
    private final DtoMapper dtoMapper;

    @PostMapping
    public BookingDTO createBooking(@RequestBody BookingDTO bookingDTO) {
        Booking booking = dtoMapper.toBookingEntity(bookingDTO);
        Booking createdBooking = bookingService.createBooking(booking);
        return dtoMapper.toBookingDTO(createdBooking);
    }

    @GetMapping("/farmer/{farmerId}")
    public List<BookingDTO> getFarmerBookings(@PathVariable String farmerId) {
        return bookingService.getBookingsByFarmer(farmerId).stream()
                .map(dtoMapper::toBookingDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/provider/{providerId}")
    public List<BookingDTO> getProviderBookings(@PathVariable String providerId) {
        return bookingService.getBookingsByProvider(providerId).stream()
                .map(dtoMapper::toBookingDTO)
                .collect(Collectors.toList());
    }

    @PutMapping("/{bookingId}/status")
    public BookingDTO updateStatus(@PathVariable String bookingId, @RequestParam String status) {
        Booking updatedBooking = bookingService.updateBookingStatus(bookingId, status);
        return dtoMapper.toBookingDTO(updatedBooking);
    }
}
