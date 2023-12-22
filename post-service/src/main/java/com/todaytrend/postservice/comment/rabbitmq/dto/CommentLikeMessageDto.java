package com.todaytrend.postservice.comment.rabbitmq.dto;

import lombok.Data;

@Data
public class CommentLikeMessageDto {
    private Long commentId;
    private String sender;
    private String receiver;
    private String content;
}
