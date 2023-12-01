package com.todaytrend.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseImgAndNicknameDto {
    private String profile_image;
    private String nickname;

    public ResponseImgAndNicknameDto(String profile_image, String nickname){
        this.profile_image = profile_image;
        this.nickname = nickname;
    }

}
