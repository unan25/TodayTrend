package com.todaytrend.notificationservice.dto;

import com.todaytrend.notificationservice.entity.Notification;
import com.todaytrend.notificationservice.entity.NotificationType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CommentCreateDto {
    private String content;

    private String sender;

    private String commentWriter;

    private String postWriter;

    public Notification toEntity(String receiver) {
       return  Notification.builder()
                .content(content)
                .sender(sender)
                .receiver(receiver)
               .createAt(LocalDateTime.now())
                .type(NotificationType.COMMENT_CREATE)
               .checked(false)
                .build();
    }
}
