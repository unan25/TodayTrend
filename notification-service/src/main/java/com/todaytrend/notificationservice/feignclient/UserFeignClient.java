package com.todaytrend.notificationservice.feignclient;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", url ="3.38.107.1:10000")
public interface UserFeignClient {

    @GetMapping("api/users/server/uuid/{uuid}")
    UserFeignDto findImageAndNickname(@PathVariable("uuid") String uuid);

    @GetMapping("api/users/server/nickname/{nickName}")
    String findUuid(@PathVariable("nickName")String nickName);
}
