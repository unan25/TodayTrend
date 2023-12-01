package com.todaytrend.userservice.service;

import com.todaytrend.userservice.dto.ResponseImgAndNicknameDto;
import com.todaytrend.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServerServiceImpl implements ServerService {

    private final UserRepository userRepo;

    @Override
    public String findUuid(String nickname) {
       return userRepo.findUuidByNickname(nickname);
    }

    @Override
    public ResponseImgAndNicknameDto findImgAndNickname(String UUID) {
        return userRepo.findImgAndNicknameByUuid(UUID).get(0);
    }
}
