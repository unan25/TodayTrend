package com.todaytrend.authservice.service;

import com.todaytrend.authservice.domain.LocalUser;
import com.todaytrend.authservice.domain.RefreshToken;
import com.todaytrend.authservice.dto.RefreshTokenResponseDto;
import com.todaytrend.authservice.repository.LocalUserRepository;
import com.todaytrend.authservice.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final LocalUserRepository localUserRepository;

    public RefreshToken findByRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 토큰입니다."));
    }

    public void saveRefreshToken(RefreshTokenResponseDto refreshTokenResponseDto) {
        // 리프레시 토큰 DB에 저장
        LocalUser localUser = localUserRepository.findByUuid(refreshTokenResponseDto.getLocalUserUuid())
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 UUID 입니다. : " + refreshTokenResponseDto.getLocalUserUuid()));

        RefreshToken refreshTokenEntity = RefreshToken.builder()
                .localUser(localUser)
                .refreshToken(refreshTokenResponseDto.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshTokenEntity);
    }
}
