package com.agrifarms.common.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "worker_groups")
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

    public WorkerGroup() {}

    public WorkerGroup(String groupId, String ownerId, String groupName, Integer maleCount, Integer femaleCount,
                       BigDecimal pricePerMale, BigDecimal pricePerFemale, String skills, String location,
                       Integer serviceRangeKm, Boolean isAvailable, BigDecimal rating, String approvalStatus,
                       String imageUrl, List<WorkerGroupRole> roles, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.groupId = groupId;
        this.ownerId = ownerId;
        this.groupName = groupName;
        this.maleCount = maleCount;
        this.femaleCount = femaleCount;
        this.pricePerMale = pricePerMale;
        this.pricePerFemale = pricePerFemale;
        this.skills = skills;
        this.location = location;
        this.serviceRangeKm = serviceRangeKm;
        this.isAvailable = isAvailable;
        this.rating = rating;
        this.approvalStatus = approvalStatus;
        this.imageUrl = imageUrl;
        this.roles = roles;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getGroupId() { return groupId; }
    public void setGroupId(String groupId) { this.groupId = groupId; }

    public String getOwnerId() { return ownerId; }
    public void setOwnerId(String ownerId) { this.ownerId = ownerId; }

    public String getGroupName() { return groupName; }
    public void setGroupName(String groupName) { this.groupName = groupName; }

    public Integer getMaleCount() { return maleCount; }
    public void setMaleCount(Integer maleCount) { this.maleCount = maleCount; }

    public Integer getFemaleCount() { return femaleCount; }
    public void setFemaleCount(Integer femaleCount) { this.femaleCount = femaleCount; }

    public BigDecimal getPricePerMale() { return pricePerMale; }
    public void setPricePerMale(BigDecimal pricePerMale) { this.pricePerMale = pricePerMale; }

    public BigDecimal getPricePerFemale() { return pricePerFemale; }
    public void setPricePerFemale(BigDecimal pricePerFemale) { this.pricePerFemale = pricePerFemale; }

    public String getSkills() { return skills; }
    public void setSkills(String skills) { this.skills = skills; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public Integer getServiceRangeKm() { return serviceRangeKm; }
    public void setServiceRangeKm(Integer serviceRangeKm) { this.serviceRangeKm = serviceRangeKm; }

    public Boolean getIsAvailable() { return isAvailable; }
    public void setIsAvailable(Boolean isAvailable) { this.isAvailable = isAvailable; }

    public BigDecimal getRating() { return rating; }
    public void setRating(BigDecimal rating) { this.rating = rating; }

    public String getApprovalStatus() { return approvalStatus; }
    public void setApprovalStatus(String approvalStatus) { this.approvalStatus = approvalStatus; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public List<WorkerGroupRole> getRoles() { return roles; }
    public void setRoles(List<WorkerGroupRole> roles) { this.roles = roles; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
