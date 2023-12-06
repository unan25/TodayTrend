package com.todaytrend.postservice.comment.feignClient;

import com.todaytrend.postservice.comment.feignClient.dto.UserFeignDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "USER-SERVICE", url = "localhost:8080/user/server")
public interface UserFeignClient {
    @GetMapping("uuid/{UUID}")
    public UserFeignDto findImgAndNickname(@PathVariable("UUID")String uuid);

}
