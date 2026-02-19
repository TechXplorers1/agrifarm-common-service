package com.agrifarms.common.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    @Id
    @Column(name = "booking_id")
    @UuidGenerator
    private String bookingId;

    @Column(name = "farmer_id", nullable = false)
    private String farmerId;

    @Column(name = "provider_id", nullable = false)
    private String providerId;

    @Column(name = "asset_id")
    private String assetId;

    @Column(name = "asset_type")
    private String assetType; // Equipment, Service, Transport

    @Column(name = "booking_date")
    private LocalDateTime bookingDate;

    @Column(name = "scheduled_start_time")
    private LocalDateTime scheduledStartTime;

    @Column(name = "scheduled_end_time")
    private LocalDateTime scheduledEndTime;

    private String status; // PENDING, CONFIRMED, COMPLETED, CANCELLED

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Column(name = "location_lat")
    private BigDecimal locationLat;

    @Column(name = "location_lng")
    private BigDecimal locationLng;

    @Column(name = "address_text")
    private String addressText;

    private String notes;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
