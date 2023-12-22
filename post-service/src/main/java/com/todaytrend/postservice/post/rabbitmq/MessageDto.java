package com.todaytrend.postservice.post.rabbitmq;

import lombok.Data;

@Data
public class MessageDto {

    private String sender;
    private String content;
    private Long postId;
    private String commentWriter;
    private String postWriter;
}
