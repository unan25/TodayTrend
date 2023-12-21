package com.todaytrend.userservice.controller;

import com.todaytrend.userservice.service.ServerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users/server")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ServerController {

    private final ServerService serverService;
    @GetMapping("nickname/{nickName}")
    public ResponseEntity<?> findUuid(@PathVariable("nickName")String nickName){
        return new ResponseEntity<>(serverService.findUuid(nickName), HttpStatus.OK);
    }

    @GetMapping("uuid/{uuid}")
    public ResponseEntity<?> findImgAndNickname(@PathVariable("uuid")String uuid){
        return new ResponseEntity<>(serverService.findImgAndNickname(uuid), HttpStatus.OK);
    }

}
