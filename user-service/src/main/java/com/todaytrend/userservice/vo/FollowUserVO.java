package com.todaytrend.userservice.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FollowUserVO {

    private String uuid;
    private String nickName;
    private String profileImage;

}
