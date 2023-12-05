package com.todaytrend.userservice.controller;

import com.todaytrend.userservice.dto.RequestCreateUserDto;
import com.todaytrend.userservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users")
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
        return new ResponseEntity(HttpStatus.CREATED);
    }

    // 닉네임 중복 체크
    @GetMapping("/checkNickname")
    public ResponseEntity<?> checkEmail(@RequestParam String nickname) {
        boolean isDuplicated = userService.isNicknameDuplicated(nickname);
        if (isDuplicated) { // 중복 시 409 반응
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 사용 중인 닉네임 입니다.");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body("사용 가능한 닉네임 입니다.");
        }
    }
}
