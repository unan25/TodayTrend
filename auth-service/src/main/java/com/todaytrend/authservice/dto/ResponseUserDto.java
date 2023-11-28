package com.todaytrend.authservice.dto;

import com.todaytrend.authservice.domain.enum_.UserType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResponseUserDto {
    private String uuid;
    private UserType userType;
}
