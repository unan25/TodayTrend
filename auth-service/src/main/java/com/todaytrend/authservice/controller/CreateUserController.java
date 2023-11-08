package com.todaytrend.authservice.controller;

import com.todaytrend.authservice.dto.RequestCreateUserDto;
import com.todaytrend.authservice.service.CreateUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth-service")
@RequiredArgsConstructor
public class CreateUserController {

    private final CreateUserService createUserService;

    @GetMapping("health-check")
    public String healthCheck(){
        return "Auth-service is available.";
    }

    @PostMapping("signup")
    public ResponseEntity<?> createUser(@Valid @RequestBody RequestCreateUserDto requestCreateUserDto) {
        createUserService.createUser(requestCreateUserDto);
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
