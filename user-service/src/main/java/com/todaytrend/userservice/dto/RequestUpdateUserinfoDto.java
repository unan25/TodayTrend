package com.todaytrend.userservice.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class RequestUpdateUserinfoDto {
    private String uuid;
    private String name;
    private String nickname;
    private String website;
    private String introduction;
    private String profileImage;

    public RequestUpdateUserinfoDto(String uuid, String name, String nickname,
                                    String introduction, String website, String profileImage) {
        this.name = name;
        this.nickname = nickname;
        this.website = website;
        this.introduction = introduction;
        this.profileImage = profileImage;
    }

}
