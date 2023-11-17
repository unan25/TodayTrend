package com.todaytrend.userservice.controller;

import com.todaytrend.userservice.dto.RequestCreateUserDto;
import com.todaytrend.userservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user-service")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("health-check")
    public String healthCheck(){
        return "user-service is available";
    }

    @PostMapping("signup")
    public ResponseEntity<?> createUser(@Valid @RequestBody RequestCreateUserDto requestCreateUserDto){
        userService.createUser(requestCreateUserDto);
        return ResponseEntity.ok("회원 가입 완료");
    }
}
