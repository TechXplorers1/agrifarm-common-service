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
    private Boolean isAvailable;
    private BigDecimal rating;
    private String approvalStatus;
    private String imageUrl;
    private String ownerProfileImageUrl;

    public ServiceOfferingDTO() {}

    public ServiceOfferingDTO(String serviceId, String ownerId, String ownerName, String serviceType, String businessName,
                              String description, String equipmentUsed, BigDecimal priceRate, Boolean operatorIncluded,
                              String location, Boolean isAvailable, BigDecimal rating, String approvalStatus, String imageUrl, String ownerProfileImageUrl) {
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
        this.isAvailable = isAvailable;
        this.rating = rating;
        this.approvalStatus = approvalStatus;
        this.imageUrl = imageUrl;
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
