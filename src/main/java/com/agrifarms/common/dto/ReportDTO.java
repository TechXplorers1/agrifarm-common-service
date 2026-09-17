package com.agrifarms.common.dto;

import java.time.LocalDateTime;

public class ReportDTO {
    private String id;
    private String reporterUserId;
    private String reportedItemId;
    private String reportedItemName;
    private String reportedProviderId;
    private String reason;
    private String details;
    private Boolean blocked;
    private String status;
    private LocalDateTime createdAt;
    private String timestamp;

    public ReportDTO() {}

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

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
}
