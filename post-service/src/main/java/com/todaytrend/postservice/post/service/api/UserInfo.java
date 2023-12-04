package com.todaytrend.postservice.post.service.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "server1", url = "http://localhost:8000")
public interface UserInfo {

    @GetMapping("/server1/test")
    String testFeign();

}
