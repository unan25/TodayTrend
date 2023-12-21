package com.todaytrend.notificationservice.dto;

import com.todaytrend.notificationservice.entity.Notification;
import com.todaytrend.notificationservice.entity.NotificationType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class FollowDto {
    private String sender;
    private String receiver;

    public Notification toEntity() {
        return  Notification.builder()
                .sender(sender)
                .receiver(receiver)
                .createAt(LocalDateTime.now())
                .type(NotificationType.FOLLOW)
                .checked(false)
                .build();
    }
}
