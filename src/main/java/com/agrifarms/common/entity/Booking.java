package com.agrifarms.common.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
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

    public Booking() {}

    public Booking(String bookingId, String farmerId, String providerId, String assetId, String assetType,
                   LocalDateTime bookingDate, LocalDateTime scheduledStartTime, LocalDateTime scheduledEndTime,
                   String status, BigDecimal totalAmount, BigDecimal locationLat, BigDecimal locationLng,
                   String addressText, String notes) {
        this.bookingId = bookingId;
        this.farmerId = farmerId;
        this.providerId = providerId;
        this.assetId = assetId;
        this.assetType = assetType;
        this.bookingDate = bookingDate;
        this.scheduledStartTime = scheduledStartTime;
        this.scheduledEndTime = scheduledEndTime;
        this.status = status;
        this.totalAmount = totalAmount;
        this.locationLat = locationLat;
        this.locationLng = locationLng;
        this.addressText = addressText;
        this.notes = notes;
    }

    public String getBookingId() { return bookingId; }
    public void setBookingId(String bookingId) { this.bookingId = bookingId; }

    public String getFarmerId() { return farmerId; }
    public void setFarmerId(String farmerId) { this.farmerId = farmerId; }

    public String getProviderId() { return providerId; }
    public void setProviderId(String providerId) { this.providerId = providerId; }

    public String getAssetId() { return assetId; }
    public void setAssetId(String assetId) { this.assetId = assetId; }

    public String getAssetType() { return assetType; }
    public void setAssetType(String assetType) { this.assetType = assetType; }

    public LocalDateTime getBookingDate() { return bookingDate; }
    public void setBookingDate(LocalDateTime bookingDate) { this.bookingDate = bookingDate; }

    public LocalDateTime getScheduledStartTime() { return scheduledStartTime; }
    public void setScheduledStartTime(LocalDateTime scheduledStartTime) { this.scheduledStartTime = scheduledStartTime; }

    public LocalDateTime getScheduledEndTime() { return scheduledEndTime; }
    public void setScheduledEndTime(LocalDateTime scheduledEndTime) { this.scheduledEndTime = scheduledEndTime; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }

    public BigDecimal getLocationLat() { return locationLat; }
    public void setLocationLat(BigDecimal locationLat) { this.locationLat = locationLat; }

    public BigDecimal getLocationLng() { return locationLng; }
    public void setLocationLng(BigDecimal locationLng) { this.locationLng = locationLng; }

    public String getAddressText() { return addressText; }
    public void setAddressText(String addressText) { this.addressText = addressText; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
