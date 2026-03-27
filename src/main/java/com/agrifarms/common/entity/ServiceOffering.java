package com.agrifarms.common.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "service_offerings")
public class ServiceOffering {
    @Id
    @Column(name = "service_id")
    @UuidGenerator
    private String serviceId;

    @Column(name = "owner_id", nullable = false)
    private String ownerId;

    @Column(name = "service_type")
    private String serviceType; // Ploughing, Harvesting, etc.

    @Column(name = "business_name")
    private String businessName;

    private String description;

    @Column(name = "equipment_used")
    private String equipmentUsed;

    @Column(name = "price_rate")
    private BigDecimal priceRate;

    @Column(name = "operator_included")
    private Boolean operatorIncluded;

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

    public ServiceOffering() {}

    public ServiceOffering(String serviceId, String ownerId, String serviceType, String businessName, String description,
                           String equipmentUsed, BigDecimal priceRate, Boolean operatorIncluded, String location,
                           Boolean isAvailable, BigDecimal rating, String approvalStatus, String imageUrl) {
        this.serviceId = serviceId;
        this.ownerId = ownerId;
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
    }

    public String getServiceId() { return serviceId; }
    public void setServiceId(String serviceId) { this.serviceId = serviceId; }

    public String getOwnerId() { return ownerId; }
    public void setOwnerId(String ownerId) { this.ownerId = ownerId; }

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

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
