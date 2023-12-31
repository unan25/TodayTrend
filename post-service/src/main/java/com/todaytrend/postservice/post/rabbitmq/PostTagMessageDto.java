package com.todaytrend.postservice.post.rabbitmq;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostTagMessageDto {

    private String sender;
    private String receiver;
    private String content;
    private Long postId;
}
