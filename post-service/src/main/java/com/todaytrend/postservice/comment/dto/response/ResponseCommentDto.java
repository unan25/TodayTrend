package com.todaytrend.postservice.comment.dto.response;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ResponseCommentDto {

    private Long commentId;
    private String content;
    private LocalDateTime createAt;
    private Long parentId;

    //유저
    private String uuid;
    private String nickname;
    private String profileImage;

}
