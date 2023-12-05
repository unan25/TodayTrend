package com.todaytrend.postservice.post.dto.CRUD;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ResponsePostDetailDto {
    private Integer statusCode;
    private String message;
    private Long postId;
    private String postUserUUID;
    private String profileImage;
    private String nickName;
    private String content;
    private LocalDateTime createdAt;
    private List<String> postImgs;
}
