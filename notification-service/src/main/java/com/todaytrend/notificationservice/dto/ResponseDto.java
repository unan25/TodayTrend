package com.todaytrend.notificationservice.dto;

import com.todaytrend.notificationservice.entity.Notification;
import com.todaytrend.notificationservice.entity.NotificationType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ResponseDto {

    private Long NotificationId;

    private String content;

    private String sender;
    private String senderImage;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    private LocalDateTime createAt;

}
