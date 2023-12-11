package com.todaytrend.postservice.post.feign.img;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseImageDto {
    private Long postId;
    private String imageUrl;
    private List<String> imageUrlList;

}
