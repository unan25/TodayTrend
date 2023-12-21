package com.todaytrend.postservice.comment.rabbitmq.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentTagMessageDto {
    private String sender; //태그 한사람
    private String receiver; //태그 당한사람 (알림받을사람)
    private String content; //댓글 내용
}
