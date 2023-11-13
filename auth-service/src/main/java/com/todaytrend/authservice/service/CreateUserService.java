package com.todaytrend.authservice.service;

import com.todaytrend.authservice.domain.LocalUser;
import com.todaytrend.authservice.dto.RequestUserDto;
import com.todaytrend.authservice.repository.LocalUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateUserService {

    private final LocalUserRepository localUserRepository;

    public void createUser(RequestUserDto requestUserDto) {
        // 이메일 중복 체크
        if(localUserRepository.findByEmail(requestUserDto.getEmail()).isPresent())
            throw new IllegalArgumentException("이미 등록된 이메일 입니다.");
        
        LocalUser localUser = requestUserDto.toEntity();
        localUserRepository.save(localUser);
    }
}
