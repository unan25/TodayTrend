package com.todaytrend.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FollowResponseDto {

    private String result;

    private String errMessage;

    public FollowResponseDto(String result) {
        this.result = result;
    }

    public static FollowResponseDto followed() {
        return new FollowResponseDto("followed", null);
    }

    public static FollowResponseDto unfollowed() {
        return new FollowResponseDto("unfollowed", null);
    }

}
