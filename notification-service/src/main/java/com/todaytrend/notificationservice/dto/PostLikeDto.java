package com.todaytrend.notificationservice.dto;

import com.todaytrend.notificationservice.entity.Notification;
import com.todaytrend.notificationservice.entity.NotificationType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostLikeDto {
    private String sender;
    private String receiver;
    private String content;

    public Notification toEntity() {
        return  Notification.builder()
                .sender(sender)
                .receiver(receiver)
                .content(content)
                .createdAt(LocalDateTime.now())
                .type(NotificationType.POST_LIKE)
                .build();
    }
}
