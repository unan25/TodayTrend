package com.todaytrend.notificationservice.feignclient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserFeignDto {
    private String profileImage;
    private String nickname;

}
