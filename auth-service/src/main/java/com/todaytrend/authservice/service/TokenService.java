package com.todaytrend.authservice.service;

import com.todaytrend.authservice.config.jwt.TokenInfo;
import com.todaytrend.authservice.config.jwt.TokenProvider;
import com.todaytrend.authservice.domain.UserInterface;
import com.todaytrend.authservice.repository.LocalUserRepository;
import com.todaytrend.authservice.repository.SocialUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenProvider tokenProvider;
    private final LocalUserRepository localUserRepository;
    private final SocialUserRepository socialUserRepository;


    public TokenInfo refreshAccessToken(String refreshToken) {
        // 리프레시 토큰 유효성 검사
        if (!tokenProvider.validToken(refreshToken)) {
            throw new IllegalArgumentException("유효하지 않은 리프레시 토큰입니다.");
        }
        // 토큰에서 사용자 정보 추출
        String uuid = tokenProvider.getUserUuid(refreshToken);
        // Social 에서 찾고 없다면 Local에서 찾기 (Social 을 이용한 회원이 더 많을 것 같음)
        UserInterface user = socialUserRepository.findByUuid(uuid)
                // 객체가 존재하면, 이를 UserInterface로 변환
                .map(localUser -> (UserInterface) localUser)
                .orElseGet(() -> localUserRepository.findByUuid(uuid)
                        .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 사용자 입니다.")));
        // 새로운 액세스 토큰 생성 및 쿠키에 저장
        return tokenProvider.generateToken(user, Duration.ofHours(1), "access_token");
    }

}
