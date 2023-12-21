package com.todaytrend.userservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.todaytrend.userservice.dto.*;
import com.todaytrend.userservice.service.FollowService;
import com.todaytrend.userservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final FollowService followService;

    @GetMapping("health-check")
    public String healthCheck(){
        return "user-service is available";
    }

    @PostMapping("signup")
    public ResponseEntity<?> createUser(@Valid @RequestBody RequestCreateUserDto requestCreateUserDto){
        userService.createUser(requestCreateUserDto);
        
        log.info("UserInfo 작성 완료");
        return new ResponseEntity(HttpStatus.CREATED);
    }

    // 닉네임 중복 체크
    @GetMapping("checkNickname")
    public ResponseEntity<?> checkEmail(@RequestParam String nickname) {
        boolean isDuplicated = userService.isNicknameDuplicated(nickname);
        if (isDuplicated) { // 중복 시 409 반응
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 사용 중인 닉네임 입니다.");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body("사용 가능한 닉네임 입니다.");
        }
    }

    // 닉네임으로 닉네임, 프로필 이미지 조회
    @GetMapping("nickname/{nickname}")
    public ResponseEntity<List<ResponseNicknameListDto>> getNickname(@PathVariable String nickname) {
        List<ResponseNicknameListDto> nicknameListDtos = userService.getNicknameAndProfileImage(nickname); // 서비스 메소드 호출
        log.info("닉네임으로 조회");
        return ResponseEntity.status(HttpStatus.OK).body(nicknameListDtos); // 바디에 담아 반환
    }

    // uuid로 닉네임, 프로필 이미지 조회
    @GetMapping("uuid/{uuid}")
    public ResponseEntity<ResponseImgAndNicknameDto> getProfileImage(@PathVariable String uuid) {
        ResponseImgAndNicknameDto user = userService.getProfileImage(uuid);
        log.info("uuid로 조회");
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    // uuid로 myPage 조회
    @GetMapping("profile/{nickname}")
    public ResponseEntity<ResponseUserDto> getAll(@PathVariable String nickname){
        ResponseUserDto user = userService.getAll(nickname);
        log.info("프로필 조회");
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
    
    // 프로필 수정 (이름, 닉네임, 웹 링크, 소개, 프로필 이미지)
    @PutMapping("updateProfile")
    public ResponseEntity<?> updateUserProfile(@RequestParam String uuid, @RequestParam(required = false) String name,
                                  @RequestParam(required = false) String nickname,
                                  @RequestParam(required = false) String website,
                                  @RequestParam(required = false) String introduction,
                                  @RequestParam(required = false) String profileImage) {
        userService.updateUserProfile(uuid, name, nickname, website, introduction, profileImage);
        log.info("프로필 수정 완료");
        return ResponseEntity.status(HttpStatus.OK).body("프로필 수정 완료");
    }

    // 프로필 이미지 삭제 (기본 이미지로 변경)
    @PatchMapping("deleteProfileImage/{uuid}")
    public ResponseEntity<?> updateProfileImageToDefault(@PathVariable String uuid) {
        ResponseProfileImageDto responseProfileImageDto = userService.deleteProfileImage(uuid);

        log.info("프로필 이미지 삭제 (기본 이미지로 변경)");
        return ResponseEntity.status(HttpStatus.OK).body(responseProfileImageDto);
    }

    /* ------------------------------------- Follow ------------------------------------- */

    @PostMapping("follow")
    public ResponseEntity<FollowResponseDto> follow(@RequestBody FollowRequestDto followRequestDto) throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.OK).body(followService.follow(followRequestDto));
    }

    @PostMapping("follow-check")
    public ResponseEntity<Boolean> checkFollowed(@RequestBody FollowRequestDto followRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(followService.checkFollow(followRequestDto));
    }

    @GetMapping("follower-count/{uuid}")
    public ResponseEntity<Long> countFollower(@PathVariable String uuid) {
        System.out.println("팔로워 목록 조회");
        return ResponseEntity.status(HttpStatus.OK).body(followService.follwerCount(uuid));
    }

    @GetMapping("following-count/{uuid}")
    public ResponseEntity<Long> countFollowing(@PathVariable String uuid) {
        return ResponseEntity.status(HttpStatus.OK).body(followService.followingCount(uuid));
    }

    @GetMapping("follower-list/{uuid}")
    public ResponseEntity<?> followerList(@PathVariable String uuid) {
        return ResponseEntity.status(HttpStatus.OK).body(followService.getFollowerList(uuid));
    }

    @GetMapping("following-list/{uuid}")
    public ResponseEntity<?> followingList(@PathVariable String uuid) {
        return ResponseEntity.status(HttpStatus.OK).body(followService.getFollowingList(uuid));
    }

}
