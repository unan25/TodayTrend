package com.todaytrend.postservice.post.feign.user;

import lombok.Data;

@Data
public class UserFeignDto {
    private String profileImage;
    private String nickname;
}
