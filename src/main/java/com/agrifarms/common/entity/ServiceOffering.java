package com.agrifarms.common.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "services")
@Data
@NoArgsConstructor
@AllArgsConstructor
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

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
