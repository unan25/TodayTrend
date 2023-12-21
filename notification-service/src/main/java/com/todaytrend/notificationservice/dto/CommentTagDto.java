package com.todaytrend.notificationservice.dto;

import com.todaytrend.notificationservice.entity.Notification;
import com.todaytrend.notificationservice.entity.NotificationType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentTagDto {
    private String sender;
    private String receiver;
    private String content;

    public Notification toEntity(String uuid) {
        return  Notification.builder()
                .sender(sender)
                .receiver(receiver)
                .content(content)
                .createAt(LocalDateTime.now())
                .type(NotificationType.COMMENT_TAG)
                .checked(false)
                .build();
    }
}
