package com.todaytrend.authservice.controller;

import com.todaytrend.authservice.config.jwt.TokenProvider;
import com.todaytrend.authservice.domain.LocalUser;
import com.todaytrend.authservice.dto.LoginResponseDto;
import com.todaytrend.authservice.dto.RequestUserDto;
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
public class LocalUserController {

    private final UserService userService;
    private final TokenService tokenService;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @GetMapping("health-check")
    public String healthCheck(){
        return "Auth-service is available.";
    }

    // 회원가입, 회원 가입 시 uuid body에 반환
    @PostMapping("signup")
    public ResponseEntity<?> createUser(@Valid @RequestBody RequestUserDto requestUserDto) {
        String userUuid = userService.createUser(requestUserDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userUuid);
    }

    // login 성공 시 Access, Refresh 토큰 발급
    @PostMapping("login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody RequestUserDto requestUserDto) {
        LoginResponseDto loginResponseDto = userService.login(requestUserDto);

        // 헤더 토큰 추가
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + loginResponseDto.getAccessToken());
        headers.add("Refresh-Token", loginResponseDto.getRefreshToken());

        return new ResponseEntity<>(loginResponseDto, headers, HttpStatus.OK);
    }

    @PostMapping("logout")
    public ResponseEntity<?> logout(@RequestBody RequestUserDto requestUserDto, HttpServletRequest request, HttpServletResponse response){
//        String token = request.getHeader("Authorization").substring("Bearer ".length());
//        String refreshToken = request.getHeader("Refresh-Token");
//        userService.logout(/*token, */refreshToken);

        userService.logout(requestUserDto.getUuid());


        // 로그아웃 처리
//        SecurityContextHolder.getContext().setAuthentication(null);
//        HttpSession session = request.getSession(false);
//        if (session != null) {
//            session.invalidate();
//        }
//        for (Cookie cookie : request.getCookies()) {
//            cookie.setMaxAge(0);
//            response.addCookie(cookie);
//        }

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("delete/{uuid}")
    public ResponseEntity<?> deleteUser(@PathVariable String uuid, @RequestParam String password, HttpServletRequest request, HttpServletResponse response) {
        userService.deleteUser(uuid, password);

        // 엑세스 토큰 무효화, 리프레시 토큰 삭제 및 로그아웃 처리
        String token = request.getHeader("Authorization").substring("Bearer ".length());
        String refreshToken = request.getHeader("Refresh-Token");
        userService.logout(/*token, */refreshToken);

        // 회원 탈퇴 후 로그아웃 처리
        SecurityContextHolder.getContext().setAuthentication(null);
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        for (Cookie cookie : request.getCookies()) {
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }

        return ResponseEntity.ok().build();
    }

}
