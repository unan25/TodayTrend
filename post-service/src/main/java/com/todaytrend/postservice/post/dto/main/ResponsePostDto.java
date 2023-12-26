package com.todaytrend.postservice.post.dto.main;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponsePostDto implements Serializable {
    private Long postId;
    private String imageUrl;
}