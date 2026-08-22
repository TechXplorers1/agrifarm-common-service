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
    private String houseNo;
    private String street;
    private String village;
    private String district;
    private String state;
    private String country;
    private String pincode;
    private Boolean isAvailable;
    private BigDecimal rating;
    private String approvalStatus;
    private String imageUrl;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String ownerProfileImageUrl;
    private BigDecimal operatorPrice;
    private String ownerBusinessName;
    private String brand;
    private String model;
    private Integer yearOfManufacture;
    private BigDecimal pricePerKm;
    private BigDecimal pricePerHour;
    private String vehicleCondition;
    private Integer jobsCompleted;

    public TransportVehicleDTO() {}

    public TransportVehicleDTO(String vehicleId, String ownerId, String ownerName, String vehicleType, String vehicleNumber,
                               String loadCapacity, BigDecimal pricePerKmOrTrip, Boolean driverIncluded, String serviceArea,
                               String location, String houseNo, String street, String village, String district, String state, String country, String pincode, Boolean isAvailable, BigDecimal rating, String approvalStatus, String imageUrl, BigDecimal latitude, BigDecimal longitude, String ownerProfileImageUrl, BigDecimal operatorPrice) {
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
        this.houseNo = houseNo;
        this.street = street;
        this.village = village;
        this.district = district;
        this.state = state;
        this.country = country;
        this.pincode = pincode;
        this.isAvailable = isAvailable;
        this.rating = rating;
        this.approvalStatus = approvalStatus;
        this.imageUrl = imageUrl;
        this.latitude = latitude;
        this.longitude = longitude;
        this.ownerProfileImageUrl = ownerProfileImageUrl;
        this.operatorPrice = operatorPrice;
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

    public String getHouseNo() { return houseNo; }
    public void setHouseNo(String houseNo) { this.houseNo = houseNo; }

    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }

    public String getVillage() { return village; }
    public void setVillage(String village) { this.village = village; }

    public String getDistrict() { return district; }
    public void setDistrict(String district) { this.district = district; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getPincode() { return pincode; }
    public void setPincode(String pincode) { this.pincode = pincode; }

    public Boolean getIsAvailable() { return isAvailable; }
    public void setIsAvailable(Boolean isAvailable) { this.isAvailable = isAvailable; }

    public BigDecimal getRating() { return rating; }
    public void setRating(BigDecimal rating) { this.rating = rating; }

    public String getApprovalStatus() { return approvalStatus; }
    public void setApprovalStatus(String approvalStatus) { this.approvalStatus = approvalStatus; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public BigDecimal getLatitude() { return latitude; }
    public void setLatitude(BigDecimal latitude) { this.latitude = latitude; }

    public BigDecimal getLongitude() { return longitude; }
    public void setLongitude(BigDecimal longitude) { this.longitude = longitude; }

    public String getOwnerProfileImageUrl() { return ownerProfileImageUrl; }
    public void setOwnerProfileImageUrl(String ownerProfileImageUrl) { this.ownerProfileImageUrl = ownerProfileImageUrl; }

    public BigDecimal getOperatorPrice() { return operatorPrice; }
    public void setOperatorPrice(BigDecimal operatorPrice) { this.operatorPrice = operatorPrice; }

    public String getOwnerBusinessName() { return ownerBusinessName; }
    public void setOwnerBusinessName(String ownerBusinessName) { this.ownerBusinessName = ownerBusinessName; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public Integer getYearOfManufacture() { return yearOfManufacture; }
    public void setYearOfManufacture(Integer yearOfManufacture) { this.yearOfManufacture = yearOfManufacture; }

    public BigDecimal getPricePerKm() { return pricePerKm; }
    public void setPricePerKm(BigDecimal pricePerKm) { this.pricePerKm = pricePerKm; }

    public BigDecimal getPricePerHour() { return pricePerHour; }
    public void setPricePerHour(BigDecimal pricePerHour) { this.pricePerHour = pricePerHour; }

    public String getVehicleCondition() { return vehicleCondition; }
    public void setVehicleCondition(String vehicleCondition) { this.vehicleCondition = vehicleCondition; }

    public Integer getJobsCompleted() { return jobsCompleted; }
    public void setJobsCompleted(Integer jobsCompleted) { this.jobsCompleted = jobsCompleted; }

    private String deactivationReason;

    public String getDeactivationReason() { return deactivationReason; }
    public void setDeactivationReason(String deactivationReason) { this.deactivationReason = deactivationReason; }
}
