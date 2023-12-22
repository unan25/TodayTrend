package com.todaytrend.notificationservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Notification {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;

    private String content;

    private String receiver;

    private String sender;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    @CreatedDate
    private LocalDateTime createAt;

    private Boolean checked;

    public void check(){
        this.checked = true;
    }
}
