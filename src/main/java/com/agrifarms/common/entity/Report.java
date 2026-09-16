package com.agrifarms.common.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Entity
@Table(name = "reports")
public class Report {

    @Id
    @Column(name = "id")
    @UuidGenerator
    private String id;

    @Column(name = "reporter_user_id", nullable = false)
    private String reporterUserId;

    @Column(name = "reported_item_id", nullable = false)
    private String reportedItemId;

    @Column(name = "reported_item_name")
    private String reportedItemName;

    @Column(name = "reported_provider_id", nullable = false)
    private String reportedProviderId;

    @Column(name = "reason", nullable = false, length = 500)
    private String reason;

    @Column(name = "details", length = 2000)
    private String details;

    @Column(name = "blocked")
    private Boolean blocked = false;

    @Column(name = "status", nullable = false)
    private String status = "PENDING"; // PENDING, REVIEWED, RESOLVED, DISMISSED

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (status == null) {
            status = "PENDING";
        }
        if (blocked == null) {
            blocked = false;
        }
    }

    public Report() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getReporterUserId() { return reporterUserId; }
    public void setReporterUserId(String reporterUserId) { this.reporterUserId = reporterUserId; }

    public String getReportedItemId() { return reportedItemId; }
    public void setReportedItemId(String reportedItemId) { this.reportedItemId = reportedItemId; }

    public String getReportedItemName() { return reportedItemName; }
    public void setReportedItemName(String reportedItemName) { this.reportedItemName = reportedItemName; }

    public String getReportedProviderId() { return reportedProviderId; }
    public void setReportedProviderId(String reportedProviderId) { this.reportedProviderId = reportedProviderId; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }

    public Boolean getBlocked() { return blocked; }
    public void setBlocked(Boolean blocked) { this.blocked = blocked; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
