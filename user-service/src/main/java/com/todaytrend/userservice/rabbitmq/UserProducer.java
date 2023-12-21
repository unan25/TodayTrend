package com.todaytrend.userservice.rabbitmq;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserProducer {
    private final RabbitTemplate rabbitTemplate;

    public void sendFollowMessage(String message) {
        rabbitTemplate.convertAndSend("NC_FOLLOW_QUEUE", message);
    }
}
