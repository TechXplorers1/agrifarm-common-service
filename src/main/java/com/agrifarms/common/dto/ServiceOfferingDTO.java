package com.agrifarms.common.dto;


import java.math.BigDecimal;

public class ServiceOfferingDTO {
    private String serviceId;
    private String ownerId;
    private String ownerName;
    private String serviceType;
    private String businessName;
    private String description;
    private String equipmentUsed;
    private BigDecimal priceRate;
    private Boolean operatorIncluded;
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
    private Integer jobsCompleted;

    public ServiceOfferingDTO() {}

    public ServiceOfferingDTO(String serviceId, String ownerId, String ownerName, String serviceType, String businessName,
                              String description, String equipmentUsed, BigDecimal priceRate, Boolean operatorIncluded,
                              String location, String houseNo, String street, String village, String district, String state, String country, String pincode, Boolean isAvailable, BigDecimal rating, String approvalStatus, String imageUrl, BigDecimal latitude, BigDecimal longitude, String ownerProfileImageUrl) {
        this.serviceId = serviceId;
        this.ownerId = ownerId;
        this.ownerName = ownerName;
        this.serviceType = serviceType;
        this.businessName = businessName;
        this.description = description;
        this.equipmentUsed = equipmentUsed;
        this.priceRate = priceRate;
        this.operatorIncluded = operatorIncluded;
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

    public String getServiceId() { return serviceId; }
    public void setServiceId(String serviceId) { this.serviceId = serviceId; }

    public String getOwnerId() { return ownerId; }
    public void setOwnerId(String ownerId) { this.ownerId = ownerId; }

    public String getOwnerName() { return ownerName; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }

    public String getServiceType() { return serviceType; }
    public void setServiceType(String serviceType) { this.serviceType = serviceType; }

    public String getBusinessName() { return businessName; }
    public void setBusinessName(String businessName) { this.businessName = businessName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getEquipmentUsed() { return equipmentUsed; }
    public void setEquipmentUsed(String equipmentUsed) { this.equipmentUsed = equipmentUsed; }

    public BigDecimal getPriceRate() { return priceRate; }
    public void setPriceRate(BigDecimal priceRate) { this.priceRate = priceRate; }

    public Boolean getOperatorIncluded() { return operatorIncluded; }
    public void setOperatorIncluded(Boolean operatorIncluded) { this.operatorIncluded = operatorIncluded; }

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

    public Integer getJobsCompleted() { return jobsCompleted; }
    public void setJobsCompleted(Integer jobsCompleted) { this.jobsCompleted = jobsCompleted; }
}
