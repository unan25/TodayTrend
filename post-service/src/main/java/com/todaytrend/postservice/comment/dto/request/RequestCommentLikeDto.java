package com.todaytrend.postservice.comment.dto.request;

import com.todaytrend.postservice.comment.entity.CommentLike;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestCommentLikeDto {

    private Long commentId;
    private String uuid;

    public CommentLike toEntity() {
        return CommentLike.builder()
                .commentId(commentId)
                .uuid(uuid)
                .build();
    }

}
