package com.agrifarms.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDTO {
    private String bookingId;
    private String farmerId;
    private String providerId;
    private String assetId;
    private String assetType;
    private LocalDateTime bookingDate;
    private LocalDateTime scheduledStartTime;
    private LocalDateTime scheduledEndTime;
    private String status;
    private BigDecimal totalAmount;
    private BigDecimal locationLat;
    private BigDecimal locationLng;
    private String addressText;
    private String notes;
}
