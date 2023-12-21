package com.todaytrend.notificationservice.dto;

import com.todaytrend.notificationservice.entity.NotificationType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class RequestDto {
    private Long NotificationId;
}
