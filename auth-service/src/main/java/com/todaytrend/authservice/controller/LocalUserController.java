package com.todaytrend.authservice.controller;

import com.todaytrend.authservice.dto.LoginResponseDto;
import com.todaytrend.authservice.dto.RequestUserDto;
import com.todaytrend.authservice.service.TokenService;
import com.todaytrend.authservice.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class LocalUserController {

    private final UserService userService;
    private final TokenService tokenService;

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
    public ResponseEntity<LoginResponseDto> login(@RequestBody RequestUserDto requestUserDto, HttpServletResponse response) {
        LoginResponseDto loginResponseDto = userService.login(requestUserDto);

        // 헤더에 토큰 추가
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + loginResponseDto.getAccessToken());
        headers.add("Refresh-Token", loginResponseDto.getRefreshToken());

        return new ResponseEntity<>(loginResponseDto, headers, HttpStatus.OK);
    }

}
