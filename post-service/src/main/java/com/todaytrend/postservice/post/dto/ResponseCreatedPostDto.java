package com.todaytrend.postservice.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseCreatedPostDto {
    private Integer statusCode;
    private String message;
    private Long postId;
}
