package com.agrifarms.common.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "equipment")
public class Equipment {

    @Id
    @Column(name = "id")
    @UuidGenerator
    private String equipmentId;

    @Column(name = "user_id", nullable = false)
    private String ownerId;

    @Column(name = "name", nullable = false)
    private String name;

    private String category; // Tractor, Harvester, etc.

    @Column(name = "brand_model")
    private String brandModel;

    @Column(name = "owner_business_name")
    private String ownerBusinessName;

    private String brand;

    private String model;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "condition_status")
    private String conditionStatus;

    @Column(name = "price_per_hour")
    private BigDecimal pricePerHour;

    @Column(name = "operator_available")
    private Boolean operatorAvailable;

    @Column(name = "operator_price")
    private BigDecimal operatorPrice;

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

    public Equipment() {
    }

    public Equipment(String equipmentId, String ownerId, String category, String brandModel, String conditionStatus,
            BigDecimal pricePerHour, Boolean operatorAvailable, String location, Boolean isAvailable,
            BigDecimal rating, String approvalStatus, String imageUrl) {
        this.equipmentId = equipmentId;
        this.ownerId = ownerId;
        this.category = category;
        this.brandModel = brandModel;
        this.name = brandModel != null ? brandModel : category;
        this.conditionStatus = conditionStatus;
        this.pricePerHour = pricePerHour;
        this.operatorAvailable = operatorAvailable;
        this.location = location;
        this.isAvailable = isAvailable;
        this.rating = rating;
        this.approvalStatus = approvalStatus;
        this.imageUrl = imageUrl;
    }

    public String getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrandModel() {
        return brandModel;
    }

    public void setBrandModel(String brandModel) {
        this.brandModel = brandModel;
    }

    public String getConditionStatus() {
        return conditionStatus;
    }

    public void setConditionStatus(String conditionStatus) {
        this.conditionStatus = conditionStatus;
    }

    public BigDecimal getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(BigDecimal pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public Boolean getOperatorAvailable() {
        return operatorAvailable;
    }

    public void setOperatorAvailable(Boolean operatorAvailable) {
        this.operatorAvailable = operatorAvailable;
    }

    public BigDecimal getOperatorPrice() {
        return operatorPrice;
    }

    public void setOperatorPrice(BigDecimal operatorPrice) {
        this.operatorPrice = operatorPrice;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    private BigDecimal latitude;
    private BigDecimal longitude;

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

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
