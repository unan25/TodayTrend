package com.todaytrend.apigatewayserver.config;

import com.todaytrend.apigatewayserver.config.exceptionpath.ExceptionPathManager;
import com.todaytrend.apigatewayserver.config.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;

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
                                        .pathMatchers("/api/auth/health-check").hasAuthority("ADMIN")
                                        .pathMatchers("/api/users/**").hasAuthority("USER")
//                                .pathMatchers("/api/auth/health-check").authenticated()
//                                .pathMatchers("/api/**").permitAll()
                                        .anyExchange().permitAll()
//                                .anyExchange().authenticated()
                )
                .csrf(csrfSpec -> csrfSpec.disable())
                .formLogin(formLoginSpec -> formLoginSpec.disable())
                .httpBasic(httpBasicSpec -> httpBasicSpec.disable())
                .logout(logoutSpec -> logoutSpec.disable());
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

}
