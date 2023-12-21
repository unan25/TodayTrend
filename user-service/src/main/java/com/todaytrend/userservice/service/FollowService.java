package com.todaytrend.userservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.todaytrend.userservice.domain.Follow;
import com.todaytrend.userservice.dto.FollowRequestDto;
import com.todaytrend.userservice.dto.FollowResponseDto;
import com.todaytrend.userservice.rabbitmq.FollowMessageDto;
import com.todaytrend.userservice.rabbitmq.UserProducer;
import com.todaytrend.userservice.repository.FollowRepository;
import com.todaytrend.userservice.repository.UserRepository;
import com.todaytrend.userservice.vo.FollowUserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;
    private final UserProducer userProducer;
    private final ObjectMapper objectMapper;

    public FollowResponseDto follow(FollowRequestDto followRequestDto) throws JsonProcessingException {

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
        String message = objectMapper.writeValueAsString(FollowMessageDto.builder().sender(followRequestDto.getFollowerId())
                .receiver(followRequestDto.getFollowingId()).build());
        userProducer.sendFollowMessage(message);

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

        var userList = new ArrayList<FollowUserVO>();

        followerList
                .forEach(
                        follower ->
                                userRepository.findByUuid(follower)
                                        .ifPresent(user -> userList.add(user.toFollowVo())
                        )
                );

        return userList;
    }

    public List<?> getFollowingList(String uuid) {
        var followingList = followRepository.findFollowingIdsByFollowerId(uuid);

        var userList = new ArrayList<FollowUserVO>();

        followingList
                .forEach(
                        following ->
                                userRepository.findByUuid(following)
                                        .ifPresent(user -> userList.add(user.toFollowVo())
                )
        );

        return userList;
    }


}
