package com.todaytrend.apigatewayserver.config;

import com.todaytrend.apigatewayserver.config.exceptionpath.ExceptionPathManager;
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
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import reactor.core.publisher.Mono;

@Configuration
@RequiredArgsConstructor
@EnableWebFluxSecurity
@Slf4j
public class WebSecurityConfig {

    @Bean
    public SecurityWebFilterChain
    springSecurityFilterChain(ServerHttpSecurity http, ExceptionPathManager exceptionPathManager
                              ,ServerAuthenticationConverter cookieServerAuthenticationConverter
                              ,ReactiveAuthenticationManager authenticationManager) {

        AuthenticationWebFilter authenticationWebFilter = new AuthenticationWebFilter(authenticationManager);
        authenticationWebFilter.setServerAuthenticationConverter(cookieServerAuthenticationConverter);

        authenticationWebFilter.setRequiresAuthenticationMatcher(serverWebExchange -> {
            String path = serverWebExchange.getRequest().getPath().value();
            if (exceptionPathManager.getExceptionPaths().contains(path)) {
                return ServerWebExchangeMatcher.MatchResult.notMatch();
            }
            return ServerWebExchangeMatcher.MatchResult.match();
        });
        log.info("시큐티리 적용");
        http
                .authorizeExchange(exchanges ->
                        exchanges
                                .pathMatchers(exceptionPathManager.getExceptionPaths().toArray(new String[0])).permitAll()
                                .pathMatchers("/img/**", "/css/**", "/js/**", "/ts/**").permitAll()
                                .pathMatchers("/api/**").hasAuthority("USER")
                                .anyExchange().permitAll()
                )
                .addFilterAt(authenticationWebFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .logout(logoutSpec -> logoutSpec
                        .logoutUrl("/api/auth/logout")
                        .logoutSuccessHandler(logoutSuccessHandler()))
                .exceptionHandling(
                        exceptionHandlingSpec -> exceptionHandlingSpec.authenticationEntryPoint((exchange, ex) -> Mono.fromRunnable(() -> {
                                    log.error("SecurityWebFilterChain 401 ", exchange.getRequest().getURI(), ex);
                                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                                }))
                        .accessDeniedHandler((exchange, denied) -> Mono.fromRunnable(() -> {
                            log.info("SecurityWebFilterChain 403 ", exchange.getRequest().getURI(), denied);
                            exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                        })));
        return http.build();
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
