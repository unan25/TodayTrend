package com.todaytrend.authservice.service;

import com.todaytrend.authservice.domain.SocialUser;
import com.todaytrend.authservice.dto.CreateSocialUserDto;
import com.todaytrend.authservice.dto.LoginResponseDto;
import com.todaytrend.authservice.repository.SocialUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SocialUserService {

    private final SocialUserRepository socialUserRepository;

    public LoginResponseDto login(CreateSocialUserDto createSocialUserDto) {

        SocialUser user =
                socialUserRepository.findByEmail(createSocialUserDto.getEmail()).orElse(null);

        if (user == null) {
            return LoginResponseDto.builder().uuid(socialUserRepository.save(createSocialUserDto.toEntity()).getUuid()).build();
        }

        return null;
    }

}
