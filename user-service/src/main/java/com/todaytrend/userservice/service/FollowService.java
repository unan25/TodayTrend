package com.todaytrend.userservice.service;

import com.todaytrend.userservice.domain.Follow;
import com.todaytrend.userservice.dto.FollowRequestDto;
import com.todaytrend.userservice.dto.FollowResponseDto;
import com.todaytrend.userservice.repository.FollowRepository;
import com.todaytrend.userservice.repository.UserRepository;
import com.todaytrend.userservice.vo.FollowUserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    public FollowResponseDto follow(FollowRequestDto followRequestDto) {

        System.out.println(followRequestDto.getFollowerId());
        System.out.println(followRequestDto.getFollowingId());

        Follow followed = followRepository.findByFollowerIdAndFollowingId(
                followRequestDto.getFollowerId(),
                followRequestDto.getFollowingId()
        ).orElse(null);

        if(followed != null) {
                followRepository.delete(followed);

                return FollowResponseDto.unfollowed();
        }

        followRepository.save(followRequestDto.toEntity());

        return FollowResponseDto.followed();
    }

    public boolean checkFollow(FollowRequestDto followRequestDto){

        Follow followed =
                followRepository.findByFollowerIdAndFollowingId(
                        followRequestDto.getFollowerId(),
                        followRequestDto.getFollowingId()
                ).orElse(null);

        return followed == null;
    }

    public Long follwerCount(String followingId) {
        return followRepository.countByFollowingId(followingId);
    }

    public Long followingCount(String followerId) {
        return followRepository.countByFollowerId(followerId);
    }

    public List<?> getFollowerList(String uuid) {
        var followerList = followRepository.findFollowerIdsByFollowingId(uuid);

        System.out.println(followerList.get(0));

        var userList = new ArrayList<FollowUserVO>();

        followerList
                .forEach(
                        follower ->
                                userRepository.findByUuid(follower)
                                        .ifPresent(e -> userList.add(e.toFollowVo())
                        )
                );

        return userList;
    }

    public List<?> getFollowingList(String uuid) {
        var followingList = followRepository.findFollowingIdsByFollowerId(uuid);

        var userList = new ArrayList<FollowUserVO>();

        followingList.forEach(
                following ->
                        userRepository.findByUuid(following)
                                .ifPresent(user -> userList.add(user.toFollowVo())
                )
        );

        return userList;
    }


}
