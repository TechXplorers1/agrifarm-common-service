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
    private String location;
    private Boolean isAvailable;
    private BigDecimal rating;
    private String approvalStatus;
    private String imageUrl;
    private String ownerProfileImageUrl;

    public EquipmentDTO() {}

    public EquipmentDTO(String equipmentId, String ownerId, String ownerName, String category, String brandModel,
                        String conditionStatus, BigDecimal pricePerHour, Boolean operatorAvailable, String location,
                        Boolean isAvailable, BigDecimal rating, String approvalStatus, String imageUrl, String ownerProfileImageUrl) {
        this.equipmentId = equipmentId;
        this.ownerId = ownerId;
        this.ownerName = ownerName;
        this.category = category;
        this.brandModel = brandModel;
        this.conditionStatus = conditionStatus;
        this.pricePerHour = pricePerHour;
        this.operatorAvailable = operatorAvailable;
        this.location = location;
        this.isAvailable = isAvailable;
        this.rating = rating;
        this.approvalStatus = approvalStatus;
        this.imageUrl = imageUrl;
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
