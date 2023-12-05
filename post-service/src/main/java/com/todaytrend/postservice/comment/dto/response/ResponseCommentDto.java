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
    private LocalDateTime createAt;

    private String uuid;
    private String nickname;
    private String profileImage;

    private int replyCommentCount;
    private int commentLikeCount;
    private boolean isLiked;


}
