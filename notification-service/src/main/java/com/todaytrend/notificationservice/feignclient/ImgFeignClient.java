package com.todaytrend.notificationservice.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "image-service", url = "3.38.93.126:12000") //이미지 서비스 페인
public interface ImgFeignClient {
    @GetMapping("api/images/{postId}")
    ImgFeignDto getImageByPostId(@PathVariable Long postId);
}