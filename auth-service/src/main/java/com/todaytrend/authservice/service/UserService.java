package com.todaytrend.authservice.service;

import com.todaytrend.authservice.config.jwt.TokenProvider;
import com.todaytrend.authservice.domain.LocalUser;
import com.todaytrend.authservice.dto.LoginResponseDto;
import com.todaytrend.authservice.dto.RequestUserDto;
import com.todaytrend.authservice.dto.ResponseUserDto;
import com.todaytrend.authservice.repository.LocalUserRepository;
import com.todaytrend.authservice.repository.RefreshTokenRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.Duration;

@RequiredArgsConstructor
@Service
public class UserService {

    private final LocalUserRepository localUserRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final TokenProvider tokenProvider;
    private final TokenService tokenService;
    private final RefreshTokenService refreshTokenService;

    public ResponseUserDto createUser(RequestUserDto requestUserDto) {
        // 이메일 중복 체크
        if(localUserRepository.findByEmail(requestUserDto.getEmail()).isPresent())
            throw new IllegalArgumentException("이미 등록된 이메일 입니다.");

        LocalUser localUser = requestUserDto.toEntity();
        localUserRepository.save(localUser);

        return ResponseUserDto.builder()
                .userType(localUser.getUserType())
                .uuid(localUser.getUuid())
                .build();
    }

    // Login
    public LoginResponseDto login(RequestUserDto requestUserDto, HttpServletResponse response) {
        // 사용자 정보 조회
        LocalUser localUser = localUserRepository.findByEmail(requestUserDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재 하지않는 유저입니다."));
        
        // 비밀번호 검증
        if (!bCryptPasswordEncoder.matches(requestUserDto.getPassword(), localUser.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        tokenProvider.generateToken(localUser, Duration.ofHours(1), "access_token", response); // 액세스 토큰 생성 및 쿠키에 저장
        tokenProvider.generateToken(localUser, Duration.ofDays(7), "refresh_token", response); // 리프레시 토큰 생성 및 쿠키에 저장

        return LoginResponseDto.builder()
                .uuid(localUser.getUuid())
                .role(localUser.getRole())
                .userType(localUser.getUserType())
                .build();
    }

    public void logout(HttpServletResponse response) {
        Cookie accessTokenCookie = new Cookie("access_token", null);
        accessTokenCookie.setMaxAge(0);
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setPath("/");

        Cookie refreshTokenCookie = new Cookie("refresh_token", null);
        refreshTokenCookie.setMaxAge(0);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setPath("/");

        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);
    }

    public void deactivateUser(String uuid, String password) {
        LocalUser localUser = localUserRepository.findByUuid(uuid)
                .orElseThrow(()-> new NotFoundException("존재하지 않는 유저입니다."));
        if (!bCryptPasswordEncoder.matches(password, localUser.getPassword())) {
            throw new IllegalArgumentException("패스워드가 불일치 합니다.");
        }

        localUser.deactivate();
        localUserRepository.save(localUser);
    }

    public LocalUser findByLocalUserId(Long localUserId) {
        return localUserRepository.findByLocalUserId(localUserId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
    }

    public LocalUser findByEmail(String email){
        return localUserRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일 입니다."));
    }

    public LocalUser findByUuid(String uuid) {
        return localUserRepository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원 입니다."));
    }
}
