package com.todaytrend.notificationservice.repository;

import com.todaytrend.notificationservice.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification,Long> {
    List<Notification> findByReceiverAndCheckedIsFalse(String receiver);
    Long countByReceiverAndCheckedIsFalse(String receiver);
    Notification findByNotificationId(Long notificationId);
}
