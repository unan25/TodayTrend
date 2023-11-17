package com.todaytrend.authservice.service;

import com.todaytrend.authservice.config.jwt.TokenProvider;
import com.todaytrend.authservice.domain.LocalUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class TokenService {

    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final LoginService loginService;

    public String createNewAccessToken(String refreshToken) {
        // 토큰 유효성 검사
        if(!tokenProvider.validToken(refreshToken)) {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }

        Long userId = refreshTokenService.findByRefreshToken(refreshToken).getLocalUserId();
        LocalUser localUser = loginService.findByLocalUserId(userId);

        return tokenProvider.generateToken(localUser, Duration.ofHours(2));
    }
}
