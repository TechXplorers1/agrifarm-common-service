package com.agrifarms.common.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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

    public BookingDTO() {}

    public BookingDTO(String bookingId, String farmerId, String providerId, String assetId, String assetType,
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
}
