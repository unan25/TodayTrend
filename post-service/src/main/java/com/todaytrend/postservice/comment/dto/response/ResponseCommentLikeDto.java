package com.todaytrend.postservice.comment.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseCommentLikeDto {
    private Long commentId;
    private int commentLikeCount;
    private boolean isLiked;
}
