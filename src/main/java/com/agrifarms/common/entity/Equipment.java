package com.agrifarms.common.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "equipment")
public class Equipment {
    @Id
    @Column(name = "equipment_id")
    @UuidGenerator
    private String equipmentId;

    @Column(name = "owner_id", nullable = false)
    private String ownerId;

    private String category; // Tractor, Harvester, etc.

    @Column(name = "brand_model")
    private String brandModel;

    @Column(name = "condition_status")
    private String conditionStatus;

    @Column(name = "price_per_hour")
    private BigDecimal pricePerHour;

    @Column(name = "operator_available")
    private Boolean operatorAvailable;

    private String location;

    @Column(name = "is_available")
    private Boolean isAvailable;

    private BigDecimal rating;

    @Column(name = "approval_status")
    private String approvalStatus;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public Equipment() {}

    public Equipment(String equipmentId, String ownerId, String category, String brandModel, String conditionStatus,
                     BigDecimal pricePerHour, Boolean operatorAvailable, String location, Boolean isAvailable,
                     BigDecimal rating, String approvalStatus, String imageUrl) {
        this.equipmentId = equipmentId;
        this.ownerId = ownerId;
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
    }

    public String getEquipmentId() { return equipmentId; }
    public void setEquipmentId(String equipmentId) { this.equipmentId = equipmentId; }

    public String getOwnerId() { return ownerId; }
    public void setOwnerId(String ownerId) { this.ownerId = ownerId; }

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

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
