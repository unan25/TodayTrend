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
    private Long postId;

    public Notification toEntity(String uuid) {
        return  Notification.builder()
                .sender(sender)
                .receiver(uuid)
                .content(content)
                .createdAt(LocalDateTime.now())
                .type(NotificationType.COMMENT_TAG)
                .postId(postId)
                .build();
    }
}
