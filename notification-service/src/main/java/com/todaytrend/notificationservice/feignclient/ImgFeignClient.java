package com.todaytrend.notificationservice.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(value = "IMAGE-SERVICE")
public interface ImgFeignClient {
    @GetMapping("api/images/{postId}")
    ImgFeignDto getImageByPostId(@PathVariable Long postId);
}