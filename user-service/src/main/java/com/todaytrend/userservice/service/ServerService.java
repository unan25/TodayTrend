package com.todaytrend.userservice.service;

import com.todaytrend.userservice.dto.ResponseImgAndNicknameDto;

public interface ServerService {

    String findUuid(String nickname);

    ResponseImgAndNicknameDto findImgAndNickname(String UUID);

}
