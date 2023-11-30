package com.todaytrend.postservice.comment.dto.request;

import com.todaytrend.postservice.comment.entity.Comment;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class RequestCommentDto {

    private Long postId;

    private String userUuid;

    private Long parentId;

    private String content;

    public Comment toEntity() {
        return Comment.builder()
                .postId(postId)
                .userUuid(userUuid)
                .parentId(parentId)
                .content(content)
                .createAt(LocalDateTime.now())
                .build();
    }

}
