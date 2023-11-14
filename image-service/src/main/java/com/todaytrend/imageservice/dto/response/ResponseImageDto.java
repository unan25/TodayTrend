package com.todaytrend.imageservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseImageDto {
    private Long imageId;
    private String imageUrl;

    // post 식별자
    private Long postId;
}
