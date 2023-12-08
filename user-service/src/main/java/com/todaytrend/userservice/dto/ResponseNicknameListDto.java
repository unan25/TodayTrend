package com.todaytrend.userservice.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class ResponseNicknameListDto {
    private String uuid;
    private String profileImage;
    private String nickname;

    public ResponseNicknameListDto(String profileImage, String nickname, String uuid){
        this.profileImage = profileImage;
        this.nickname = nickname;
        this.uuid = uuid;
    }
}
