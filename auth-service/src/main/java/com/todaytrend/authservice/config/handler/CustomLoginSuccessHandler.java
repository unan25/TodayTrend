//package com.todaytrend.authservice.config.handler;
//
//import com.todaytrend.authservice.config.jwt.TokenProvider;
//import com.todaytrend.authservice.domain.LocalUser;
//import com.todaytrend.authservice.domain.RefreshToken;
//import com.todaytrend.authservice.repository.LocalUserRepository;
//import com.todaytrend.authservice.repository.RefreshTokenRepository;
//import com.todaytrend.authservice.service.UserService;
//import com.todaytrend.authservice.util.CookieUtil;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
//import org.springframework.stereotype.Component;
//import org.springframework.web.util.UriComponentsBuilder;
//
//import java.io.IOException;
//import java.time.Duration;
//
//@RequiredArgsConstructor
//@Component
//public class CustomLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
//
//    public static final String REFRESH_TOKEN_COOKIE_NAME = "refresh_token";
//    public static final Duration REFRESH_TOKEN_DURATION = Duration.ofDays(14);
//    public static final Duration ACCESS_TOKEN_DURATION = Duration.ofDays(1);
//    public static final String REDIRECT_PATH = "/articles";
//
//
//    private final TokenProvider tokenProvider;
//    private final RefreshTokenRepository refreshTokenRepository;
//    private final UserService userService;
//    private final LocalUserRepository localUserRepository;
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
//        LocalUser localUser = (LocalUser) authentication.getPrincipal();
//
//        String refreshToken = tokenProvider.generateToken(localUser, REFRESH_TOKEN_DURATION);
//        saveRefreshToken(localUser.getUuid(), refreshToken); // UUID를 사용하여 refreshToken을 저장
//        addRefreshTokenToCookie(request, response,refreshToken);
//
//        String accessToken = tokenProvider.generateToken(localUser, ACCESS_TOKEN_DURATION);
//        String targetUrl = getTargetUrl(accessToken);
//
//        getRedirectStrategy().sendRedirect(request, response, targetUrl);
//
//    }
//
//
//    // LocalUser uuid를 이용해 RefreshToken 찾은 후 없다면 재발급
//    private void saveRefreshToken(String uuid, String newRefreshToken) {
//        LocalUser localUser = localUserRepository.findByUuid(uuid)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found with UUID: " + uuid));
//
//        RefreshToken refreshToken = refreshTokenRepository.findByLocalUser_Uuid(uuid)
//                .map(entity -> entity.Update(newRefreshToken))
//                .orElse(new RefreshToken(localUser, newRefreshToken));
//
//        refreshTokenRepository.save(refreshToken);
//    }
//
//    // 생성된 리프레시 토큰을 쿠키에 저장
//    private void addRefreshTokenToCookie(HttpServletRequest request, HttpServletResponse response, String refreshToken) {
//        int cookieMaxAge = (int) REFRESH_TOKEN_DURATION.toSeconds();
//
//        CookieUtil.deleteCookie(request, response, REFRESH_TOKEN_COOKIE_NAME);
//        CookieUtil.addCookie(response, REFRESH_TOKEN_COOKIE_NAME, refreshToken, cookieMaxAge);
//    }
//
//    // 인증 관련 설정값, 쿠키 제거
////    private void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
////        super.clearAuthenticationAttributes(request);
////        authenticationRequestRopsitory.removeAuthorizationRequestCookies(request, response);
////    }
//
//    // 액세스 토큰을 패스에 추가
//    private String getTargetUrl(String token) {
//        return UriComponentsBuilder.fromUriString(REDIRECT_PATH)
//                .queryParam("token", token)
//                .build()
//                .toUriString();
//    }
//}
