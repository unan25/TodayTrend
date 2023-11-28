package com.todaytrend.authservice.service;

import com.todaytrend.authservice.config.jwt.TokenProvider;
import com.todaytrend.authservice.domain.LocalUser;
import com.todaytrend.authservice.repository.LocalUserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenProvider tokenProvider;
    private final LocalUserRepository localUserRepository;


    public void refreshAccessToken(String refreshToken, HttpServletResponse response) {
        // 리프레시 토큰 유효성 검사
        if (!tokenProvider.validToken(refreshToken)) {
            throw new IllegalArgumentException("유효하지 않은 리프레시 토큰입니다.");
        }

        // 토큰에서 사용자 정보 추출
        String uuid = tokenProvider.getLocalUserUuid(refreshToken);
        LocalUser localUser = localUserRepository.findByUuid(uuid)
                .orElseThrow(()-> new IllegalArgumentException("유효하지 않은 사용자입니다."));

        // 새로운 액세스 토큰 생성 및 쿠키에 저장
        tokenProvider.generateToken(localUser, Duration.ofHours(1), "access_token", response);
    }
    

//    public String createNewAccessToken(LocalUser localUser) {
//        // 새로운 엑세스 토큰 생성
//        Duration expiredAt = Duration.ofHours(1); // 만료 기간 1시간
//        return tokenProvider.generateToken(localUser, expiredAt);
//    }
//
//    public RefreshTokenResponseDto createNewRefreshToken(LocalUser localUser) {
//        // 새로운 리프레시 토큰 생성
//        Duration expiredAt = Duration.ofDays(1); // 만료 기간 1일
//        String refreshToken = tokenProvider.generateToken(localUser, expiredAt);
//
//        // 리프레시 토큰 DTO에 저장
//        return RefreshTokenResponseDto.builder()
//                .refreshToken(refreshToken)
//                .localUserUuid(localUser.getUuid())
//                .build();
//    }
}
