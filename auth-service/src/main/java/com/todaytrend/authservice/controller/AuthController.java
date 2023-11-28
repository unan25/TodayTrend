package com.todaytrend.authservice.controller;

import com.todaytrend.authservice.config.jwt.TokenProvider;
import com.todaytrend.authservice.dto.LoginResponseDto;
import com.todaytrend.authservice.dto.RequestUserDto;
import com.todaytrend.authservice.dto.ResponseUserDto;
import com.todaytrend.authservice.repository.RefreshTokenRepository;
import com.todaytrend.authservice.service.TokenService;
import com.todaytrend.authservice.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final TokenService tokenService;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @GetMapping("health-check")
    public String healthCheck(){
        return "Auth-service is available.";
    }

    // 회원가입, 회원 가입 시 uuid, userType body에 반환
    @PostMapping("signup")
    public ResponseEntity<?> createUser(@Valid @RequestBody RequestUserDto requestUserDto) {
        ResponseUserDto responseUserDto = userService.createUser(requestUserDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseUserDto);
    }

    // login 성공 시 Access, Refresh 토큰 발급
    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody RequestUserDto requestUserDto, HttpServletResponse response) {
        LoginResponseDto loginResponseDto = userService.login(requestUserDto, response);
        return ResponseEntity.ok(loginResponseDto);
    }

    // 로그아웃
    @PostMapping("logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        userService.logout(response);
        // 204를 반환하여 로그아웃과 토큰 값 null 처리를 알림
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // 토큰 재발급
    @PostMapping("refresh")
    public ResponseEntity<Void> refreshAccessToken(@CookieValue("refresh_token") String refreshToken, HttpServletResponse response) {
        tokenService.refreshAccessToken(refreshToken, response);
        // 204를 반환하여 엑세스 토큰의 재발급 성공적 알림
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // 회원 탈퇴 (회원 상태 변경 -> false)
    // 필요 Param : uuid, password
    @PostMapping("deactivate")
    public ResponseEntity<?> deactivateUser(@RequestParam String uuid,
                                            @RequestParam String password, HttpServletResponse response){
        userService.deactivateUser(uuid, password);
        userService.logout(response);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
