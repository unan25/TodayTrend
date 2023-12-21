package com.todaytrend.postservice.comment.feignClient.dto;

import lombok.Data;

@Data
public class UserCommentFeignDto {

    private String profileImage;
    private String nickname;
    private String uuid;
}
