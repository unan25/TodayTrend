package com.todaytrend.userservice.rabbitmq;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FollowMessageDto {
    private String sender;
    private String receiver;
}
