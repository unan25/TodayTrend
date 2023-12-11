package com.todaytrend.userservice.dto;

import lombok.*;


@Getter @Builder
@AllArgsConstructor
public class ResponseUserDto {

    private String name;

    private String nickname;

    private String website;

    private String introduction;

    private String profileImage;
}
