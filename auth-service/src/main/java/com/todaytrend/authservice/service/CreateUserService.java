package com.todaytrend.authservice.service;

import com.todaytrend.authservice.domain.LocalUser;
import com.todaytrend.authservice.dto.RequestCreateUserDto;
import com.todaytrend.authservice.repository.AuthRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateUserService {

    private final AuthRepository authRepository;

    public void createUser(RequestCreateUserDto requestCreateUserDto) {
        LocalUser localUser = requestCreateUserDto.toEntity();
        authRepository.save(localUser);
    }
}
