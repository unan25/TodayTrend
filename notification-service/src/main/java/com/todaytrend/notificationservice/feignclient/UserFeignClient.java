package com.todaytrend.notificationservice.feignclient;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "USER-SERVICE")
public interface UserFeignClient {

    @GetMapping("api/users/server/uuid/{uuid}")
    UserFeignDto findImageAndNickname(@PathVariable("uuid") String uuid);

    @GetMapping("api/users/server/nickname/{nickName}")
    String findUuid(@PathVariable("nickName")String nickName);
}
