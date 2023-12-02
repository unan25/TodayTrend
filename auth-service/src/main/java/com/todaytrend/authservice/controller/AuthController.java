package com.todaytrend.authservice.controller;

import com.todaytrend.authservice.config.jwt.TokenInfo;
import com.todaytrend.authservice.domain.LocalUser;
import com.todaytrend.authservice.domain.SocialUser;
import com.todaytrend.authservice.dto.CreateSocialUserDto;
import com.todaytrend.authservice.dto.LoginResponseDto;
import com.todaytrend.authservice.dto.RequestUserDto;
import com.todaytrend.authservice.dto.ResponseUserDto;
import com.todaytrend.authservice.service.SocialUserService;
import com.todaytrend.authservice.service.TokenService;
import com.todaytrend.authservice.service.UserService;
import com.todaytrend.authservice.util.CookieUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final TokenService tokenService;
    private final SocialUserService socialUserService;
    private final CookieUtils cookieUtils;

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
        LoginResponseDto loginResponseDto = userService.login(requestUserDto);
        LocalUser user = userService.findByUuid(loginResponseDto.getUuid());

        cookieUtils.setTokenCookies(user,response);

        return ResponseEntity.ok(loginResponseDto);
    }

    // 소셜 로그인
    @PostMapping("social-login")
    public ResponseEntity<?> socialLogin(@RequestBody CreateSocialUserDto createSocialUserDto, HttpServletResponse response) {
        LoginResponseDto loginResponseDto = socialUserService.login(createSocialUserDto);

        System.out.println("userType" + loginResponseDto.getUserType());

        SocialUser user = socialUserService.findByUuid(loginResponseDto.getUuid());

        cookieUtils.setTokenCookies(user, response);

        return ResponseEntity.ok(loginResponseDto);
    }

    // 로그아웃
    @PostMapping("logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        cookieUtils.deleteCookies(response, "access_token", "refresh_token");
        // 204를 반환하여 로그아웃과 토큰 값 null 처리를 알림
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // 토큰 재발급
    @PostMapping("refresh")
    public ResponseEntity<Void> refreshAccessToken(@CookieValue("refresh_token") String refreshToken, HttpServletResponse response) {
        TokenInfo newAccessToken = tokenService.refreshAccessToken(refreshToken);
        Cookie newAccessTokenCookie = cookieUtils.createCookie("access_token", newAccessToken.getToken(), newAccessToken.getExpiresIn());

        response.addCookie(newAccessTokenCookie);
        // 204를 반환하여 엑세스 토큰의 재발급 성공적 알림
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // 회원 탈퇴 (회원 상태 변경 -> false)
    // 필요 Param : uuid, password
    @PostMapping("deactivate")
    public ResponseEntity<?> deactivateUser(@RequestParam String uuid,
                                            @RequestParam String password, HttpServletResponse response){
        userService.deactivateUser(uuid, password);
        cookieUtils.deleteCookies(response, "access_token", "refresh_token");

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

/* --------------------------- 쿠키 관련 메서드 --------------------------- */
    // 쿠키 생성
//    private Cookie createCookie(String name, String value, int maxAge) {
//        Cookie cookie = new Cookie(name, value);
//        cookie.setHttpOnly(true);
//        cookie.setMaxAge(maxAge);
//        cookie.setPath("/");
//        return cookie;
//    }
//
//    // 쿠키 설정
//    private void setTokenCookies(UserInterface user, HttpServletResponse response) {
//        TokenInfo accessTokenInfo = tokenProvider.generateToken(user, Duration.ofHours(1), "access_token");
//        TokenInfo refreshTokenInfo = tokenProvider.generateToken(user, Duration.ofDays(7), "refresh_token");
//
//        Cookie accessTokenCookie = createCookie("access_token", accessTokenInfo.getToken(), accessTokenInfo.getExpiresIn());
//        Cookie refreshTokenCookie = createCookie("refresh_token", refreshTokenInfo.getToken(), refreshTokenInfo.getExpiresIn());
//
//        response.addCookie(accessTokenCookie);
//        response.addCookie(refreshTokenCookie);
//    }
//
//    // 쿠키 삭제
//    private void deleteCookies(HttpServletResponse response, String... cookieNames) {
//        for(String name : cookieNames) {
//            Cookie cookie = new Cookie(name, null);
//            cookie.setHttpOnly(true);
//            cookie.setMaxAge(0);
//            cookie.setPath("/");
//            response.addCookie(cookie);
//        }
//    }

}

