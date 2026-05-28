package com.agrifarms.common.controller;

import com.agrifarms.common.entity.UserNotification;
import com.agrifarms.common.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "*")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserNotification>> getUserNotifications(@PathVariable String userId) {
        List<UserNotification> notifications = notificationService.getNotificationsByUserId(userId);
        return ResponseEntity.ok(notifications);
    }

    @PutMapping("/{id}/read")
    public ResponseEntity<Void> markAsRead(@PathVariable String id) {
        notificationService.markAsRead(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/user/{userId}/read-all")
    public ResponseEntity<Void> markAllAsRead(@PathVariable String userId) {
        notificationService.markAllAsRead(userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/trigger-demo")
    public ResponseEntity<UserNotification> triggerDemo(@RequestBody java.util.Map<String, String> body) {
        String userId = body.get("userId");
        String title = body.get("title");
        String message = body.get("message");
        String type = body.get("type");
        String relatedId = body.get("relatedId");
        UserNotification notification = notificationService.triggerDemo(userId, title, message, type, relatedId);
        return ResponseEntity.ok(notification);
    }
}
