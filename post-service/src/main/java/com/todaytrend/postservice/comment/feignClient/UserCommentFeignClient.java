package com.todaytrend.postservice.comment.feignClient;

import com.todaytrend.postservice.comment.feignClient.dto.UserCommentFeignDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "USER-SERVICE")
public interface UserCommentFeignClient {
    @GetMapping("api/users/server/uuid/{uuid}")
    UserCommentFeignDto findImageAndNickname(@PathVariable("uuid") String uuid);

}
