package com.todaytrend.postservice.comment.dto.response;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Data
@Builder
public class ResponseCommentDto {

    private Long commentId;

    private String content;

    private Long parentId;

    private LocalDateTime createAt;

    private String userUuid;

    private Long postId;
}
