package com.todaytrend.authservice.config.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenInfo {
    private String token;
    private int expiresIn;
}
