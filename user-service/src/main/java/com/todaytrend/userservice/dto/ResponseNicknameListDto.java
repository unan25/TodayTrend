package com.todaytrend.userservice.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class ResponseNicknameListDto {
    private String nickname;
    private String profileImage;
    private String uuid;

    public ResponseNicknameListDto(String nickname, String profileImage, String uuid){
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.uuid = uuid;
    }
}
