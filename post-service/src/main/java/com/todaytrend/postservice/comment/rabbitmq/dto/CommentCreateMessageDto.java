package com.todaytrend.postservice.comment.rabbitmq.dto;

import lombok.Data;

@Data
public class CommentCreateMessageDto {

    private String sender;
    private String content;
    private Long postId;
    private String commentWriter;
}
