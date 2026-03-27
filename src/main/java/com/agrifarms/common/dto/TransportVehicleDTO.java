package com.agrifarms.common.dto;


import java.math.BigDecimal;

public class TransportVehicleDTO {
    private String vehicleId;
    private String ownerId;
    private String ownerName;
    private String vehicleType;
    private String vehicleNumber;
    private String loadCapacity;
    private BigDecimal pricePerKmOrTrip;
    private Boolean driverIncluded;
    private String serviceArea;
    private String location;
    private Boolean isAvailable;
    private BigDecimal rating;
    private String approvalStatus;
    private String imageUrl;
    private String ownerProfileImageUrl;

    public TransportVehicleDTO() {}

    public TransportVehicleDTO(String vehicleId, String ownerId, String ownerName, String vehicleType, String vehicleNumber,
                               String loadCapacity, BigDecimal pricePerKmOrTrip, Boolean driverIncluded, String serviceArea,
                               String location, Boolean isAvailable, BigDecimal rating, String approvalStatus, String imageUrl, String ownerProfileImageUrl) {
        this.vehicleId = vehicleId;
        this.ownerId = ownerId;
        this.ownerName = ownerName;
        this.vehicleType = vehicleType;
        this.vehicleNumber = vehicleNumber;
        this.loadCapacity = loadCapacity;
        this.pricePerKmOrTrip = pricePerKmOrTrip;
        this.driverIncluded = driverIncluded;
        this.serviceArea = serviceArea;
        this.location = location;
        this.isAvailable = isAvailable;
        this.rating = rating;
        this.approvalStatus = approvalStatus;
        this.imageUrl = imageUrl;
        this.ownerProfileImageUrl = ownerProfileImageUrl;
    }

    public String getVehicleId() { return vehicleId; }
    public void setVehicleId(String vehicleId) { this.vehicleId = vehicleId; }

    public String getOwnerId() { return ownerId; }
    public void setOwnerId(String ownerId) { this.ownerId = ownerId; }

    public String getOwnerName() { return ownerName; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }

    public String getVehicleType() { return vehicleType; }
    public void setVehicleType(String vehicleType) { this.vehicleType = vehicleType; }

    public String getVehicleNumber() { return vehicleNumber; }
    public void setVehicleNumber(String vehicleNumber) { this.vehicleNumber = vehicleNumber; }

    public String getLoadCapacity() { return loadCapacity; }
    public void setLoadCapacity(String loadCapacity) { this.loadCapacity = loadCapacity; }

    public BigDecimal getPricePerKmOrTrip() { return pricePerKmOrTrip; }
    public void setPricePerKmOrTrip(BigDecimal pricePerKmOrTrip) { this.pricePerKmOrTrip = pricePerKmOrTrip; }

    public Boolean getDriverIncluded() { return driverIncluded; }
    public void setDriverIncluded(Boolean driverIncluded) { this.driverIncluded = driverIncluded; }

    public String getServiceArea() { return serviceArea; }
    public void setServiceArea(String serviceArea) { this.serviceArea = serviceArea; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public Boolean getIsAvailable() { return isAvailable; }
    public void setIsAvailable(Boolean isAvailable) { this.isAvailable = isAvailable; }

    public BigDecimal getRating() { return rating; }
    public void setRating(BigDecimal rating) { this.rating = rating; }

    public String getApprovalStatus() { return approvalStatus; }
    public void setApprovalStatus(String approvalStatus) { this.approvalStatus = approvalStatus; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getOwnerProfileImageUrl() { return ownerProfileImageUrl; }
    public void setOwnerProfileImageUrl(String ownerProfileImageUrl) { this.ownerProfileImageUrl = ownerProfileImageUrl; }
}
