package com.todaytrend.postservice.comment.rabbitmq;

import lombok.Data;

@Data
public class MessageDto {

    private String sender;
    private String content;
    private Long postId;
    private String commentWriter;
}
