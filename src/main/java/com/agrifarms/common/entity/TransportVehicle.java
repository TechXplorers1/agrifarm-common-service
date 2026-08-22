package com.agrifarms.common.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transport_vehicles")
public class TransportVehicle {

    @Id
    @Column(name = "id")
    @UuidGenerator
    private String vehicleId;

    @Column(name = "user_id", nullable = false)
    private String ownerId;

    @Column(name = "vehicle_type")
    private String vehicleType; // Mini Truck, Tractor Trolley, etc.

    @Column(name = "vehicle_number")
    private String vehicleNumber;

    @Column(name = "load_capacity")
    private String loadCapacity;

    @Column(name = "price_per_km_or_trip")
    private BigDecimal pricePerKmOrTrip;

    @Column(name = "driver_included")
    private Boolean driverIncluded;

    @Column(name = "service_area")
    private String serviceArea;

    private String location;

    @Column(name = "house_no")
    private String houseNo;

    @Column(name = "street")
    private String street;

    @Column(name = "village")
    private String village;

    @Column(name = "district")
    private String district;

    @Column(name = "state")
    private String state;

    @Column(name = "country")
    private String country;

    @Column(name = "pincode")
    private String pincode;

    @Column(name = "is_available")
    private Boolean isAvailable;

    private BigDecimal rating;

    @Column(name = "approval_status")
    private String approvalStatus;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public TransportVehicle() {}

    public TransportVehicle(String vehicleId, String ownerId, String vehicleType, String vehicleNumber,
                            String loadCapacity, BigDecimal pricePerKmOrTrip, Boolean driverIncluded,
                            String serviceArea, String location, Boolean isAvailable, BigDecimal rating,
                            String approvalStatus, String imageUrl) {
        this.vehicleId = vehicleId;
        this.ownerId = ownerId;
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
    }

    public String getVehicleId() { return vehicleId; }
    public void setVehicleId(String vehicleId) { this.vehicleId = vehicleId; }

    public String getOwnerId() { return ownerId; }
    public void setOwnerId(String ownerId) { this.ownerId = ownerId; }

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

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    private BigDecimal latitude;
    private BigDecimal longitude;

    @Column(name = "operator_price")
    private BigDecimal operatorPrice;

    @Column(name = "owner_business_name")
    private String ownerBusinessName;

    private String brand;
    private String model;

    @Column(name = "year_of_manufacture")
    private Integer yearOfManufacture;

    @Column(name = "price_per_km")
    private BigDecimal pricePerKm;

    @Column(name = "price_per_hour")
    private BigDecimal pricePerHour;

    @Column(name = "vehicle_condition")
    private String vehicleCondition;

    public BigDecimal getOperatorPrice() { return operatorPrice; }
    public void setOperatorPrice(BigDecimal operatorPrice) { this.operatorPrice = operatorPrice; }

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

    public BigDecimal getLatitude() { return latitude; }
    public void setLatitude(BigDecimal latitude) { this.latitude = latitude; }

    public BigDecimal getLongitude() { return longitude; }
    public void setLongitude(BigDecimal longitude) { this.longitude = longitude; }

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

    @jakarta.persistence.Column(name = "deactivation_reason", length = 500)
    private String deactivationReason;

    public String getDeactivationReason() { return deactivationReason; }
    public void setDeactivationReason(String deactivationReason) { this.deactivationReason = deactivationReason; }
}
