package com.todaytrend.postservice.comment.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ResponseDeleteCommentDto {

    private boolean deleted;

    private Long commentId;

    private String content;

    private Long parentId;

    private LocalDateTime createAt;

    private String userUuid;

    private Long postId;

}
