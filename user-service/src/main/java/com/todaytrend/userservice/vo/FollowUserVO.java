package com.todaytrend.userservice.vo;

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
