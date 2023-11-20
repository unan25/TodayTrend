package com.todaytrend.authservice.service;

import com.todaytrend.authservice.domain.LocalUser;
import com.todaytrend.authservice.dto.LoginResponseDto;
import com.todaytrend.authservice.dto.RefreshTokenResponseDto;
import com.todaytrend.authservice.dto.RequestUserDto;
import com.todaytrend.authservice.repository.LocalUserRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final LocalUserRepository localUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final TokenService tokenService;
    private final RefreshTokenService refreshTokenService;

    public String createUser(RequestUserDto requestUserDto) {
        // 이메일 중복 체크
        if(localUserRepository.findByEmail(requestUserDto.getEmail()).isPresent())
            throw new IllegalArgumentException("이미 등록된 이메일 입니다.");

        LocalUser localUser = requestUserDto.toEntity();
        localUserRepository.save(localUser);

        return localUser.getUuid();
    }

    // Login
    public LoginResponseDto login(RequestUserDto requestUserDto) {
        LocalUser localUser = localUserRepository.findByEmail(requestUserDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재 하지않는 유저입니다."));

        if (!bCryptPasswordEncoder.matches(requestUserDto.getPassword(), localUser.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 액세스 토큰 생성
        String accessToken = tokenService.createNewAccessToken(localUser);
        
        // 리프레시 토큰 생성 및 DB 저장
        RefreshTokenResponseDto refreshTokenResponseDto = tokenService.createNewRefreshToken(localUser);
        refreshTokenService.saveRefreshToken(refreshTokenResponseDto);

        return LoginResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshTokenResponseDto.getRefreshToken())
                .uuid(localUser.getUuid())
                .build();
    }

    public LocalUser findByLocalUserId(Long localUserId) {
        return localUserRepository.findByLocalUserId(localUserId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
    }

    public LocalUser findByEmail(String email){
        return localUserRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일 입니다."));
    }


}
