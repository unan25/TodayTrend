package com.todaytrend.postservice.dto.api;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
public class RequestRecommendPostIdDto {

    private String userUuid;
    private Integer tab;//1- 전체, 2- 팔로잉
    private List<String> CategoryNameList;

}
