package com.todaytrend.postservice.post.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", url = "localhost:8080/user/server")
public interface UserFeignClient {
    @GetMapping("uuid/{uuid}")
    UserFeignDto findImgAndNickname(@PathVariable("uuid")String uuid);
}
