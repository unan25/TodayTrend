package com.todaytrend.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ResponseProfileImageDto {
    private String uuid;
    private String currentProfileImageUrl;
}
