package com.agrifarms.common.dto;

import java.math.BigDecimal;

public class EquipmentDTO {
    private String equipmentId;
    private String ownerId;
    private String ownerName;
    private String category;
    private String brandModel;
    private String conditionStatus;
    private BigDecimal pricePerHour;
    private Boolean operatorAvailable;
    private BigDecimal operatorPrice;
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
    private String ownerBusinessName;
    private String brand;
    private String model;
    private String description;

    public EquipmentDTO() {}

    public EquipmentDTO(String equipmentId, String ownerId, String ownerName, String category, String brandModel,
                        String conditionStatus, BigDecimal pricePerHour, Boolean operatorAvailable, BigDecimal operatorPrice, String location,
                        String houseNo, String street, String village, String district, String state, String country, String pincode,
                        Boolean isAvailable, BigDecimal rating, String approvalStatus, String imageUrl, BigDecimal latitude, BigDecimal longitude, String ownerProfileImageUrl) {
        this.equipmentId = equipmentId;
        this.ownerId = ownerId;
        this.ownerName = ownerName;
        this.category = category;
        this.brandModel = brandModel;
        this.conditionStatus = conditionStatus;
        this.pricePerHour = pricePerHour;
        this.operatorAvailable = operatorAvailable;
        this.operatorPrice = operatorPrice;
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
    }

    public String getEquipmentId() { return equipmentId; }
    public void setEquipmentId(String equipmentId) { this.equipmentId = equipmentId; }

    public String getOwnerId() { return ownerId; }
    public void setOwnerId(String ownerId) { this.ownerId = ownerId; }

    public String getOwnerName() { return ownerName; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getBrandModel() { return brandModel; }
    public void setBrandModel(String brandModel) { this.brandModel = brandModel; }

    public String getConditionStatus() { return conditionStatus; }
    public void setConditionStatus(String conditionStatus) { this.conditionStatus = conditionStatus; }

    public BigDecimal getPricePerHour() { return pricePerHour; }
    public void setPricePerHour(BigDecimal pricePerHour) { this.pricePerHour = pricePerHour; }

    public Boolean getOperatorAvailable() { return operatorAvailable; }
    public void setOperatorAvailable(Boolean operatorAvailable) { this.operatorAvailable = operatorAvailable; }

    public BigDecimal getOperatorPrice() { return operatorPrice; }
    public void setOperatorPrice(BigDecimal operatorPrice) { this.operatorPrice = operatorPrice; }

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

    public String getOwnerBusinessName() { return ownerBusinessName; }
    public void setOwnerBusinessName(String ownerBusinessName) { this.ownerBusinessName = ownerBusinessName; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
