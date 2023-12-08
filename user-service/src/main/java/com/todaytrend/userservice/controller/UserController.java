package com.todaytrend.userservice.controller;

import com.todaytrend.userservice.dto.*;
import com.todaytrend.userservice.service.FollowService;
import com.todaytrend.userservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
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
        return ResponseEntity.status(HttpStatus.OK).body(nicknameListDtos); // 바디에 담아 반환
    }

    // uuid로 닉네임, 프로필 이미지 조회
    @GetMapping("uuid/{uuid}")
    public ResponseEntity<ResponseImgAndNicknameDto> getProfileImage(@PathVariable String uuid) {
        ResponseImgAndNicknameDto user = userService.getProfileImage(uuid);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    // uuid로 myPage 조회
    // todo : + 팔로잉, 팔로워 수
    @GetMapping("/myPage/{uuid}")
    public ResponseEntity<ResponseUserDto> getAll(@PathVariable String uuid){
        ResponseUserDto user = userService.getAll(uuid);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    /* ------------------------------------- Follow ------------------------------------- */

    @PostMapping("follow")
    public ResponseEntity<FollowResponseDto> follow(@RequestBody FollowRequestDto followRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(followService.follow(followRequestDto));
    }

    @PostMapping("follow-check")
    public ResponseEntity<Boolean> checkFollowed(@RequestBody FollowRequestDto followRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(followService.checkFollow(followRequestDto));
    }

    @GetMapping("follower-count/{uuid}")
    public ResponseEntity<Long> countFollower(@PathVariable String uuid) {
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
