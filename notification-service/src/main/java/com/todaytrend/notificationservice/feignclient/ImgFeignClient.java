package com.todaytrend.notificationservice.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "IMAGE_SERVICE")
public interface ImgFeignClient {
    @GetMapping("api/images/{postId}")
    ImgFeignDto getImageByPostId(@PathVariable Long postId);
}