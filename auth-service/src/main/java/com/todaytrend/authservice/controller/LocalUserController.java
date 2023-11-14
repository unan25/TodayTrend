package com.todaytrend.authservice.controller;

import com.todaytrend.authservice.dto.CreateAccessTokenRequest;
import com.todaytrend.authservice.dto.CreateAccessTokenResponse;
import com.todaytrend.authservice.dto.RequestUserDto;
import com.todaytrend.authservice.service.CreateUserService;
import com.todaytrend.authservice.service.LoginService;
import com.todaytrend.authservice.service.TokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class LocalUserController {

    private final CreateUserService createUserService;
    private final LoginService loginService;
    private final TokenService tokenService;

    @GetMapping("health-check")
    public String healthCheck(){
        return "Auth-service is available.";
    }

    @PostMapping("signup")
    public ResponseEntity<Void> createUser(@Valid @RequestBody RequestUserDto requestUserDto) {
        createUserService.createUser(requestUserDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("login")
    public String login(@RequestBody RequestUserDto requestUserDto) {
        return loginService.login(requestUserDto);
    }

    @PostMapping("token")
    public ResponseEntity<CreateAccessTokenResponse> createNewAccessToken(
            @RequestBody CreateAccessTokenRequest request) {
        String newAccessToken = tokenService.createNewAccessToken(request.getRefreshToken());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CreateAccessTokenResponse(newAccessToken));
    }

}
