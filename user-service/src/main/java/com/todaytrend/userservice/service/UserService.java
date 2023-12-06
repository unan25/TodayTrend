package com.todaytrend.userservice.service;

import com.todaytrend.userservice.domain.User;
import com.todaytrend.userservice.dto.RequestCreateUserDto;
import com.todaytrend.userservice.dto.ResponseImgAndNicknameDto;
import com.todaytrend.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void createUser(RequestCreateUserDto requestCreateUserDto){
        User user = requestCreateUserDto.toEntity();
        userRepository.save(user);
    }

    // 이메일 중복 체크
    public boolean isNicknameDuplicated(String nickname){
        return userRepository.findByNickname(nickname).isPresent();
    }

    public ResponseImgAndNicknameDto findByNickname(String nickname) {
        User user = userRepository.findByNickname(nickname)
                .orElseThrow(()-> new IllegalArgumentException("해당 닉네임의 사용자가 존재하지 않습니다. 닉네임: " + nickname));
        return  ResponseImgAndNicknameDto.builder()
                .nickname(user.getNickname())
                .profileImage(user.getProfileImage())
                .build();
    }
}
