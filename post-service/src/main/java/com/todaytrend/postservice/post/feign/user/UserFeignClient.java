package com.todaytrend.postservice.post.feign.user;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserFeignClient {
    @GetMapping("api/users/server/uuid/{uuid}")
    UserFeignDto findImgAndNickname(@PathVariable("uuid")String uuid);
}
