package com.todaytrend.postservice.comment.dto.request;

import com.todaytrend.postservice.comment.entity.Comment;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class RequestCommentDto {

    private Long postId;

    private String uuid;

    private Long parentId;

    private String content;

    private List<String> userTagList;

    public Comment toEntity() {
        return Comment.builder()
                .postId(postId)
                .uuid(uuid)
                .parentId(parentId)
                .content(content)
                .createAt(LocalDateTime.now())
                .build();
    }

}
