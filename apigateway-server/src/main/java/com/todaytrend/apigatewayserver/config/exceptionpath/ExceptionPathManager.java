package com.todaytrend.apigatewayserver.config.exceptionpath;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class ExceptionPathManager {

    private final Set<String> exceptionPaths;

    public ExceptionPathManager() {
        this.exceptionPaths = new HashSet<>(Arrays.asList(
                "/api/auth/signup",
                "/api/auth/login",
                "/api/auth/social-login",
                "/api/auth/refresh",
                "/api/auth/logout",
                "/api/auth/checkEmail",
                "/api/auth/findPassword",
                "/api/users/checkNickname",
                "/api/users/signup",
                "/api/users/findId",
                "/api/users/server/**",
                "/api/post/**",
                "/api/images/**",
                "/api/users/uuid/**",
                "/api/auth/health-check",
                "/api/users/health-check"

                ));
    }

    public Set<String> getExceptionPaths() {
        return this.exceptionPaths;
    }
}
