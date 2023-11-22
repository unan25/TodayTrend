package com.todaytrend.postservice.comment.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestCommentDto {

    private Long postId;

    private String userUuid;

    private Long parentId;

    private String content;

}
