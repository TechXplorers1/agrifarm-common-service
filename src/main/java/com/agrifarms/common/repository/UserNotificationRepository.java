package com.agrifarms.common.repository;

import com.agrifarms.common.entity.UserNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserNotificationRepository extends JpaRepository<UserNotification, String> {
    List<UserNotification> findByUserIdOrderByCreatedAtDesc(String userId);
    
    List<UserNotification> findByUserIdAndIsReadFalse(String userId);
}
