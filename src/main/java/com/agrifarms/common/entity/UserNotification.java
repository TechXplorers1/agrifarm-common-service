package com.agrifarms.common.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_notifications")
public class UserNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String userId;
    private String title;
    private String message;
    private String type;
    private String relatedId;
    
    private boolean isRead;
    private LocalDateTime createdAt;

    public UserNotification() {}

    public UserNotification(String id, String userId, String title, String message, String type, String relatedId, boolean isRead, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.message = message;
        this.type = type;
        this.relatedId = relatedId;
        this.isRead = isRead;
        this.createdAt = createdAt;
    }

    public static UserNotificationBuilder builder() {
        return new UserNotificationBuilder();
    }

    public static class UserNotificationBuilder {
        private String id;
        private String userId;
        private String title;
        private String message;
        private String type;
        private String relatedId;
        private boolean isRead;
        private LocalDateTime createdAt;

        public UserNotificationBuilder id(String id) { this.id = id; return this; }
        public UserNotificationBuilder userId(String userId) { this.userId = userId; return this; }
        public UserNotificationBuilder title(String title) { this.title = title; return this; }
        public UserNotificationBuilder message(String message) { this.message = message; return this; }
        public UserNotificationBuilder type(String type) { this.type = type; return this; }
        public UserNotificationBuilder relatedId(String relatedId) { this.relatedId = relatedId; return this; }
        public UserNotificationBuilder isRead(boolean isRead) { this.isRead = isRead; return this; }
        public UserNotificationBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }

        public UserNotification build() {
            return new UserNotification(id, userId, title, message, type, relatedId, isRead, createdAt);
        }
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getRelatedId() { return relatedId; }
    public void setRelatedId(String relatedId) { this.relatedId = relatedId; }

    public boolean isRead() { return isRead; }
    public void setRead(boolean read) { isRead = read; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
