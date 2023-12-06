package com.todaytrend.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class ResponseImgAndNicknameDto {
    private String profileImage;
    private String nickname;

    public ResponseImgAndNicknameDto(String profileImage, String nickname){
        this.profileImage = profileImage;
        this.nickname = nickname;
    }

}
