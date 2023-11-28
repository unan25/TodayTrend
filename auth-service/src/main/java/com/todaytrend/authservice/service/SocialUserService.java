package com.todaytrend.authservice.service;

import com.todaytrend.authservice.config.jwt.TokenProvider;
import com.todaytrend.authservice.domain.SocialUser;
import com.todaytrend.authservice.dto.CreateSocialUserDto;
import com.todaytrend.authservice.dto.LoginResponseDto;
import com.todaytrend.authservice.repository.SocialUserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class SocialUserService {

    private final SocialUserRepository socialUserRepository;
    private final TokenProvider tokenProvider;

    public LoginResponseDto login(CreateSocialUserDto createSocialUserDto, HttpServletResponse res) {

        SocialUser user =
                socialUserRepository.findByEmail(createSocialUserDto.getEmail()).orElse(null);

        if (user == null) {
            return LoginResponseDto.builder().uuid(socialUserRepository.save(createSocialUserDto.toEntity()).getUuid()).build();
        }

        tokenProvider.generateToken(user, Duration.ofHours(1), "access_token", res);

        return null;
    }

}
