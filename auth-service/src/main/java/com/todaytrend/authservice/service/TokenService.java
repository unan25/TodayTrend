package com.todaytrend.authservice.service;

import com.todaytrend.authservice.config.jwt.TokenProvider;
import com.todaytrend.authservice.domain.LocalUser;
import com.todaytrend.authservice.domain.RefreshToken;
import com.todaytrend.authservice.dto.RefreshTokenResponseDto;
import com.todaytrend.authservice.repository.LocalUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenProvider tokenProvider;

    public String createNewAccessToken(LocalUser localUser) {
        // 새로운 엑세스 토큰 생성
        Duration expiredAt = Duration.ofHours(1); // 만료 기간 1시간
        return tokenProvider.generateToken(localUser, expiredAt);
    }

    public RefreshTokenResponseDto createNewRefreshToken(LocalUser localUser) {
        // 새로운 리프레시 토큰 생성
        Duration expiredAt = Duration.ofDays(1); // 만료 기간 하루
        String refreshToken = tokenProvider.generateToken(localUser, expiredAt);
        
        // 리프레시 토큰 DTO에 저장
        return RefreshTokenResponseDto.builder()
                .refreshToken(refreshToken)
                .localUserUuid(localUser.getUuid())
                .build();
    }
}
