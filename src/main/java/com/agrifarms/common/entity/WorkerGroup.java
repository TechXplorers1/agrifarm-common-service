package com.agrifarms.common.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "worker_groups")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkerGroup {

    @Id
    @Column(name = "group_id")
    @UuidGenerator
    private String groupId;

    @Column(name = "owner_id", nullable = false)
    private String ownerId;

    @Column(name = "group_name")
    private String groupName;

    @Column(name = "male_count")
    private Integer maleCount;

    @Column(name = "female_count")
    private Integer femaleCount;

    @Column(name = "price_per_male")
    private BigDecimal pricePerMale;

    @Column(name = "price_per_female")
    private BigDecimal pricePerFemale;

    private String skills;
    private String location;

    @Column(name = "service_range_km")
    private Integer serviceRangeKm;

    @Column(name = "is_available")
    private Boolean isAvailable;

    private BigDecimal rating;

    @Column(name = "approval_status")
    private String approvalStatus;

    @Column(name = "image_url")
    private String imageUrl;

    @OneToMany(mappedBy = "workerGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkerGroupRole> roles;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
