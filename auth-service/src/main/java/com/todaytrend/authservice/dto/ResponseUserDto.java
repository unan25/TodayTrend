package com.todaytrend.authservice.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResponseUserDto {
    private String uuid;
    private String userType;
}
