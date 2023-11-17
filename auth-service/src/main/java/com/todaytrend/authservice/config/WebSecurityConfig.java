package com.todaytrend.authservice.config;

import com.todaytrend.authservice.config.handler.CustomLoginSuccessHandler;
import com.todaytrend.authservice.config.jwt.TokenProvider;
import com.todaytrend.authservice.repository.LocalUserRepository;
import com.todaytrend.authservice.repository.RefreshTokenRepository;
import com.todaytrend.authservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final UserService userService;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final LocalUserRepository localUserRepository;

    @Bean
    public WebSecurityCustomizer configure() { // 스프링 시큐리티 기능 비활성화
        return (web) -> web.ignoring()
                .requestMatchers("/img/**", "/css/**", "/js/**", "/ts/**");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 토큰 방식으로 인증을 하기 때문에 로그인 폼, 세션 비활성화
                .csrf(httpSecurityCsrfSpec -> httpSecurityCsrfSpec.disable())
                .formLogin(httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer.disable())
                .httpBasic(httpSecurityHttpBasicConfigurer -> httpSecurityHttpBasicConfigurer.disable())
                .logout(logoutConfigurer -> logoutConfigurer.disable())
                .sessionManagement(sessionManagementConfigurer -> sessionManagementConfigurer
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // 헤더를 확인할 커스텀 필터
                .addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                // 토큰 재발급 URL은 인증 없이 접근 가능하도록 설정.
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/**").permitAll()
                        //.requestMatchers("/api/**").authenticated() // 나머지 API URL은 인증 필요
                        .anyRequest().permitAll()
                        //.anyRequest().authenticated() // 다른 요청은 인증 필요
                );



        return http.build();
    }

    // 로그인 성공 시 실행할 토큰 발급 핸들러
    // 직접 만든 로그인에는 핸들러 필요 x , oauth에만 적용하면됨.
    // oauth 회원가입 생성 시 적용 예정
    @Bean
    public CustomLoginSuccessHandler loginSuccessHandler() {
        return new CustomLoginSuccessHandler(tokenProvider, refreshTokenRepository, userService, localUserRepository);
    }

    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter(tokenProvider);
    }

}
