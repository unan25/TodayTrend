package com.todaytrend.postservice.post.dto.CRUD;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestUpdatePostDto {
    private String content;
    private List<String> hashTagList;
    private List<String> userTagList;
    private List<Long> categoryIdList;

    @Data
    @NoArgsConstructor
    public static class RequestRecommendPostIdDto {
        private String userUuid;
        private Integer tab;//1- 전체, 2- 팔로잉
        private List<String> CategoryNameList;
    }
}
