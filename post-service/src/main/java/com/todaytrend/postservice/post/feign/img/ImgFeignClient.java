package com.todaytrend.postservice.post.feign.img;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", url = "localhost:8000/user/server")
public interface ImgFeignClient {
    @GetMapping("uuid/{uuid}")
    ImgFeignDto findImgAndNickname(@PathVariable("uuid")String uuid);
}
