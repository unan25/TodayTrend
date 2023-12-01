package com.todaytrend.userservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user/server")
public class ServerController {

    @GetMapping("{nickName}")
    public ResponseEntity<?> findUuid(@PathVariable("nickName")String nickName){


    }

}
