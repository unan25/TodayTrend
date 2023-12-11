package com.todaytrend.userservice.service;

import com.todaytrend.userservice.domain.User;
import com.todaytrend.userservice.dto.*;
import com.todaytrend.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // 회원가입 - UserInfo 작성
    public void createUser(RequestCreateUserDto requestCreateUserDto) {
        User user = requestCreateUserDto.toEntity();
        userRepository.save(user);
    }

    // 닉네임 중복 체크
    public boolean isNicknameDuplicated(String nickname) {
        return userRepository.findByNickname(nickname).isPresent();
    }

    // 닉네임으로 닉네임, 프로필 이미지, uuid 조회
    public List<ResponseNicknameListDto> getNicknameAndProfileImage(String nickname) {
        List<User> users = userRepository.findAllByNicknameStartingWith(nickname);
        List<ResponseNicknameListDto> nicknameListDtoList = new ArrayList<>();

        for (User user : users) {
            ResponseNicknameListDto nicknameListDto = ResponseNicknameListDto.builder()
                    .nickname(user.getNickname())
                    .profileImage(user.getProfileImage())
                    .uuid(user.getUuid())
                    .build();

            nicknameListDtoList.add(nicknameListDto);
        }

        return nicknameListDtoList;
    }

    // uuid로 닉네임, 프로필 이미지 조회
    public ResponseImgAndNicknameDto getProfileImage(String uuid) {
        User user = userRepository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다. uuid: " + uuid));
        return ResponseImgAndNicknameDto.builder()
                .nickname(user.getNickname())
                .profileImage(user.getProfileImage())
                .build();
    }

    // uuid로 UserInfo 전체 조회 (마이페이지)
    public ResponseUserDto getAll(String uuid) {
        User user = userRepository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다. uuid : " + uuid));
        return ResponseUserDto.builder()
                .name(user.getName())
                .nickname(user.getNickname())
                .website(user.getWebsite())
                .introduction(user.getIntroduction())
                .profileImage(user.getProfileImage())
                .build();
    }

    // 프로필 변경 (프로필 이미지, 이름, 닉네임, 자기소개, 링크)
    public void updateUserProfile(String uuid, String name, String nickname, String introduction, String website, String profileImage) {
        User user = userRepository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다. uuid : " + uuid));

        // 닉네임 변경한 경우에만 중복 검사 수행
        if (!user.getNickname().equals(nickname) && isNicknameDuplicated(nickname)) {
            throw new IllegalArgumentException("이미 사용 중인 닉네임입니다. userService.updateUserProfile");
        }
        user.updateUserInfo(name, nickname, website, introduction, profileImage);

        userRepository.save(user);
    }

    public ResponseProfileImageDto deleteProfileImage(String uuid) {
        User user = userRepository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("해당 UUID의 사용자가 존재하지 않습니다. UUID: " + uuid));
        String currentImageUrl = user.getProfileImage();

        user.changeProfileImage("https://todaytrend.s3.ap-northeast-2.amazonaws.com/profile/04dbd59a-c0e5-459c-bb2a-3b672e28c373TT_Default_Profile.jpg");

        userRepository.save(user);
        return ResponseProfileImageDto.builder()
                .uuid(user.getUuid())
                .currentProfileImageUrl(currentImageUrl).build();
    }
}
