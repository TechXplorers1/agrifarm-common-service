package com.agrifarms.common.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.agrifarms.common.dto.BookingDTO;
import com.agrifarms.common.dto.DtoMapper;
import com.agrifarms.common.entity.Booking;
import com.agrifarms.common.service.BookingService;
<<<<<<< HEAD
=======
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import com.agrifarms.common.service.ReviewService;
>>>>>>> fa3223a6cff1b8d4049d144fb586cc220be39424

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "*")
public class BookingController {

    private final BookingService bookingService;
    private final DtoMapper dtoMapper;
    private final ReviewService reviewService;

    public BookingController(BookingService bookingService, DtoMapper dtoMapper, ReviewService reviewService) {
        this.bookingService = bookingService;
        this.dtoMapper = dtoMapper;
        this.reviewService = reviewService;
    }

    @PostMapping
    public BookingDTO createBooking(@RequestBody BookingDTO bookingDTO) {
        Booking booking = dtoMapper.toBookingEntity(bookingDTO);
        Booking createdBooking = bookingService.createBooking(booking);
        return dtoMapper.toBookingDTO(createdBooking);
    }

    @GetMapping("/all")
    public List<BookingDTO> getAllBookings() {
        return bookingService.getAllBookings().stream()
                .map(dtoMapper::toBookingDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/farmer/{farmerId}")
    public List<BookingDTO> getFarmerBookings(@PathVariable("farmerId") String farmerId) {
        return bookingService.getBookingsByFarmer(farmerId).stream()
                .map(b -> {
                    BookingDTO dto = dtoMapper.toBookingDTO(b);
                    dto.setIsReviewed(reviewService.hasReviewForBooking(dto.getBookingId()));
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @GetMapping("/provider/{providerId}")
    public List<BookingDTO> getProviderBookings(@PathVariable("providerId") String providerId) {
        return bookingService.getBookingsByProvider(providerId).stream()
                .map(dtoMapper::toBookingDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/asset/{assetId}")
    public List<BookingDTO> getAssetBookings(@PathVariable("assetId") String assetId) {
        return bookingService.getBookingsByAsset(assetId).stream()
                .map(dtoMapper::toBookingDTO)
                .collect(Collectors.toList());
    }

    @PutMapping("/{bookingId}/status")
    public BookingDTO updateStatus(
            @PathVariable("bookingId") String bookingId,
            @RequestParam String status,
            @RequestParam(required = false) String cancelledBy,
            @RequestParam(required = false) String cancellationReason) {
        Booking updatedBooking = bookingService.updateBookingStatus(bookingId, status, cancelledBy, cancellationReason);
        return dtoMapper.toBookingDTO(updatedBooking);
    }
}
