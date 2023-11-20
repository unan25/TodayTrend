package com.todaytrend.authservice.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RefreshTokenResponseDto {
    private String refreshToken;
    private String localUserUuid;
}
