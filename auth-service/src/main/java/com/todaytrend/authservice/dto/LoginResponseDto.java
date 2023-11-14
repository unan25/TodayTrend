package com.todaytrend.authservice.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponseDto {

    private String token;
    private String uuid;
}
