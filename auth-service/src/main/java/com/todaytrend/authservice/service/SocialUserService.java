package com.todaytrend.authservice.service;

import com.todaytrend.authservice.domain.SocialUser;
import com.todaytrend.authservice.domain.enum_.Role;
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
            return LoginResponseDto
                    .builder()
                    .uuid(socialUserRepository.save(createSocialUserDto.toEntity()).getUuid())
                    .role(Role.USER)
                    .userType("SOCIAL")
                    .build();
        }
//        return null;
        return LoginResponseDto.builder()
                .uuid(user.getUuid())
                .role(user.getRole())
                .build();
    }

    public SocialUser findByUuid(String uuid) {
        return socialUserRepository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원 입니다."));
    }

}
