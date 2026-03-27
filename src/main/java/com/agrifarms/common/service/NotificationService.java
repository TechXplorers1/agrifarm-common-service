package com.agrifarms.common.service;

import com.agrifarms.common.entity.UserNotification;
import com.agrifarms.common.repository.UserNotificationRepository;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class NotificationService {

    private final UserNotificationRepository userNotificationRepository;

    public NotificationService(UserNotificationRepository userNotificationRepository) {
        this.userNotificationRepository = userNotificationRepository;
    }


    public void saveAndSendNotification(String userId, String fcmToken, String title, String body, String type, String relatedId, Map<String, String> data) {
        // 1. Save to database
        UserNotification userNotification = UserNotification.builder()
                .userId(userId)
                .title(title)
                .message(body)
                .type(type)
                .relatedId(relatedId)
                .isRead(false)
                .createdAt(LocalDateTime.now())
                .build();
        userNotificationRepository.save(userNotification);

        // 2. Send Push Notification if FCM token exists
        if (fcmToken != null && !fcmToken.isEmpty()) {
            sendPushNotification(fcmToken, title, body, data);
        }
    }

    public List<UserNotification> getNotificationsByUserId(String userId) {
        return userNotificationRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    public void markAsRead(String notificationId) {
        Optional<UserNotification> opt = userNotificationRepository.findById(notificationId);
        if (opt.isPresent()) {
            UserNotification notification = opt.get();
            notification.setRead(true);
            userNotificationRepository.save(notification);
        }
    }

    public void markAllAsRead(String userId) {
        List<UserNotification> unreadNotifications = userNotificationRepository.findByUserIdAndIsReadFalse(userId);
        for (UserNotification notification : unreadNotifications) {
            notification.setRead(true);
        }
        userNotificationRepository.saveAll(unreadNotifications);
    }

    public void sendPushNotification(String fcmToken, String title, String body, Map<String, String> data) {
        if (fcmToken == null || fcmToken.isEmpty()) {
            System.err.println("Cannot send notification: FCM token is missing.");
            return;
        }

        Notification notification = Notification.builder()
                .setTitle(title)
                .setBody(body)
                .build();

        Message.Builder messageBuilder = Message.builder()
                .setToken(fcmToken)
                .setNotification(notification);

        if (data != null && !data.isEmpty()) {
            messageBuilder.putAllData(data);
        }

        Message message = messageBuilder.build();

        try {
            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println("Successfully sent message: " + response);
        } catch (Exception e) {
            System.err.println("Error sending FCM message: " + e.getMessage());
        }
    }

    public void sendTopicNotification(String topic, String title, String body, Map<String, String> data) {
        Notification notification = Notification.builder()
                .setTitle(title)
                .setBody(body)
                .build();

        Message.Builder messageBuilder = Message.builder()
                .setTopic(topic)
                .setNotification(notification);

        if (data != null && !data.isEmpty()) {
            messageBuilder.putAllData(data);
        }

        Message message = messageBuilder.build();

        try {
            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println("Successfully sent message to topic " + topic + ": " + response);
        } catch (Exception e) {
            System.err.println("Error sending FCM message to topic: " + e.getMessage());
        }
    }
}
