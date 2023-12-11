package com.todaytrend.authservice.controller;

import com.todaytrend.authservice.config.jwt.TokenInfo;
import com.todaytrend.authservice.domain.LocalUser;
import com.todaytrend.authservice.domain.SocialUser;
import com.todaytrend.authservice.dto.*;
import com.todaytrend.authservice.service.*;
import com.todaytrend.authservice.util.CookieUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final UserService userService;
    private final TokenService tokenService;
    private final SocialUserService socialUserService;
    private final CookieUtils cookieUtils;
    private final EmailService emailService;
    private final UserEmailService userEmailService;

    @GetMapping("health-check")
    public String healthCheck(){
        return "Auth-service is available.";
    }

    // 회원가입, 회원 가입 시 uuid, userType body에 반환
    @PostMapping("signup")
    public ResponseEntity<?> createUser(@Valid @RequestBody RequestUserDto requestUserDto) {
        ResponseUserDto responseUserDto = userService.createUser(requestUserDto);
        log.info("LocalUser 회원가입 완료");
        return ResponseEntity.status(HttpStatus.CREATED).body(responseUserDto);
    }

    // login 성공 시 Access, Refresh 토큰 발급
    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody RequestUserDto requestUserDto, HttpServletResponse response) {
        LoginResponseDto loginResponseDto = userService.login(requestUserDto);
        LocalUser user = userService.findByUuid(loginResponseDto.getUuid());

        cookieUtils.setTokenCookies(user,response); // 토큰 생성 및 쿠키에 저장

        log.info("LocalUser 로그인 완료");
        return ResponseEntity.ok(loginResponseDto);
    }

    // 소셜 로그인
    @PostMapping("social-login")
    public ResponseEntity<?> socialLogin(@RequestBody CreateSocialUserDto createSocialUserDto, HttpServletResponse response) {
        LoginResponseDto loginResponseDto = socialUserService.login(createSocialUserDto);
        SocialUser user = socialUserService.findByUuid(loginResponseDto.getUuid());

        cookieUtils.setTokenCookies(user, response); // 토큰 생성 및 쿠키에 저장
        
        log.info("소셜 로그인 완료");
        return ResponseEntity.ok(loginResponseDto);
    }

    // 로그아웃
    @PostMapping("logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        cookieUtils.deleteCookies(response, "access_token", "refresh_token"); // 쿠키에서 토큰 삭제
        // 204를 반환하여 로그아웃과 토큰 값 null 처리를 알림
        log.info("로그아웃");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // 토큰 재발급
    @PostMapping("refresh")
    public ResponseEntity<Void> refreshAccessToken(@CookieValue("refresh_token") String refreshToken, HttpServletResponse response) {
        TokenInfo newAccessToken = tokenService.refreshAccessToken(refreshToken);
        Cookie newAccessTokenCookie = cookieUtils.createCookie("access_token", newAccessToken.getToken(), newAccessToken.getExpiresIn());

        response.addCookie(newAccessTokenCookie);
        // 204를 반환하여 엑세스 토큰의 재발급 성공적 알림
        log.info("토큰 재발급 완료");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // 회원 탈퇴 (회원 상태 변경 -> false)
    // 필요 Param : uuid, password
    @PostMapping("deactivate")
    public ResponseEntity<?> deactivateUser(@RequestParam String uuid,
                                            @RequestParam String password, HttpServletResponse response){
        userService.deactivateUser(uuid, password);
        cookieUtils.deleteCookies(response, "access_token", "refresh_token"); // 쿠키에서 토큰 삭제
        
        log.info("LocalUser 회원 탈퇴 완료");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // 이메일 중복 체크
    @GetMapping("checkEmail")
    public ResponseEntity<?> checkEmail(@RequestParam String email) {
        boolean isDuplicated = userService.isEmailDuplicated(email);
        if (isDuplicated) { // 중복 시 409 반응
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 사용 중인 이메일 입니다.");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body("사용 가능한 이메일 입니다.");
        }
    }

    // Password 찾기
    @PostMapping("findPassword")
    public ResponseEntity<?> findPwEmail(@RequestParam("userEmail") String userEmail) {
        // 임시 비밀번호 생성
        String tmpPassword = userEmailService.getTmpPassword();
        // 임시 비밀번호 저장
        userEmailService.updatePassword(tmpPassword, userEmail);
        // 메일 생성 및 전송
        MailDto mailDto = emailService.createMail(tmpPassword, userEmail);
        emailService.sendEmail(mailDto);

        log.info("임시 비밀번호 전송 완료");
        return ResponseEntity.status(HttpStatus.OK).body("임시 비밀번호 발송 완료");
    }
    
    
    // Password 변경
    // todo : dto로 parm ㄴㄴ
    @PutMapping("change-password")
    public ResponseEntity<?> changePassword(@RequestParam String uuid,
                                                    @RequestParam String currentPassword,
                                                    @RequestParam String newPassword,
                                                    @RequestParam String confirmPassword) {
        LocalUser localUser = userService.changePassword(uuid, currentPassword, newPassword, confirmPassword);
        log.info("비밀번호 변경 완료");
        return ResponseEntity.status(HttpStatus.OK).body("비밀번호 변경 완료");
    }
}

