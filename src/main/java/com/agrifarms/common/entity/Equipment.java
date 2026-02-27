package com.agrifarms.common.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "equipment")
@Data
@NoArgsConstructor
@AllArgsConstructor
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

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }
}
