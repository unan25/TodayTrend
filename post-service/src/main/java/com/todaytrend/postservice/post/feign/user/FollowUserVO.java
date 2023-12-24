package com.todaytrend.postservice.post.feign.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class FollowUserVO {

    private String uuid;
    private String nickName;
    private String profileImage;

}
