package com.todaytrend.apigatewayserver.config;

import com.todaytrend.apigatewayserver.config.exceptionpath.ExceptionPathManager;
import com.todaytrend.apigatewayserver.config.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;

@Configuration
@RequiredArgsConstructor
@EnableWebFluxSecurity
@Slf4j
public class WebSecurityConfig {

    @Bean
    public SecurityWebFilterChain
    springSecurityFilterChain(ServerHttpSecurity http, ExceptionPathManager exceptionPathManager,
                              ServerAuthenticationConverter cookieServerAuthenticationConverter,
                              ReactiveAuthenticationManager authenticationManager) {
        AuthenticationWebFilter authenticationWebFilter = new AuthenticationWebFilter(authenticationManager);
        authenticationWebFilter.setServerAuthenticationConverter(cookieServerAuthenticationConverter);
        log.info("시큐티리 적용");
        http
                .addFilterAt(authenticationWebFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .authorizeExchange(exchanges ->
                                exchanges
                                        .pathMatchers("/img/**", "/css/**", "/js/**", "/ts/**").permitAll()
                                        .pathMatchers(exceptionPathManager.getExceptionPaths().toArray(new String[0])).permitAll()
                                        .pathMatchers("/api/**").hasAuthority("USER")
                                        .anyExchange().permitAll()
//                                .anyExchange().authenticated()
                )
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .logout(logoutSpec -> logoutSpec
                        .logoutUrl("/api/auth/logout")
                        .logoutSuccessHandler(logoutSuccessHandler()));
        return http.build();
    }

    @Bean
    public ServerAuthenticationConverter cookieServerAuthenticationConverter() {
        return new CookieServerAuthenticationConverter();
    }

    @Bean
    public ReactiveAuthenticationManager authenticationManager(TokenProvider tokenProvider) {
        return new CustomReactiveAuthenticationManager(tokenProvider);
    }

    private ServerLogoutSuccessHandler logoutSuccessHandler() {
        return (exchange, authentication) -> {
            ServerHttpResponse response = exchange.getExchange().getResponse();
            response.setStatusCode(HttpStatus.OK);
            log.info("logoutSuccessHandler 실행");
            response.getHeaders().add("Set-Cookie", "access_token=; Max-Age=0; Path=/;"); // 쿠키 삭제
            log.info("access_token 삭제");
            response.getHeaders().add("Set-Cookie", "refresh_token=; Max-Age=0; Path=/;"); // 쿠키 삭제
            log.info("refresh_token 삭제");
            return response.setComplete();
        };
    }

}
