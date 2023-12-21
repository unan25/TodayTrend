package com.todaytrend.postservice.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestCheckLikedDto {
    private String uuid;
    private Long postId;
}
