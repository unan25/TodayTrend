package com.todaytrend.authservice.controller;

import com.todaytrend.authservice.dto.LoginResponseDto;
import com.todaytrend.authservice.dto.RequestUserDto;
import com.todaytrend.authservice.service.CreateUserService;
import com.todaytrend.authservice.service.LoginService;
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

    @GetMapping("health-check")
    public String healthCheck(){
        return "Auth-service is available.";
    }

    @PostMapping("signup")
    public ResponseEntity<RequestUserDto> createUser(@Valid @RequestBody RequestUserDto requestUserDto) {
        createUserService.createUser(requestUserDto);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PostMapping("login")
    public String login(@RequestBody RequestUserDto requestUserDto) {
        return loginService.login(requestUserDto);
    }


}
