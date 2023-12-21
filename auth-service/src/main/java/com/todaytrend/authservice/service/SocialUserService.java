package com.todaytrend.authservice.service;

import com.todaytrend.authservice.domain.SocialUser;
import com.todaytrend.authservice.domain.enum_.Role;
import com.todaytrend.authservice.dto.CreateSocialUserDto;
import com.todaytrend.authservice.dto.LoginResponseDto;
import com.todaytrend.authservice.repository.SocialUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SocialUserService {

    private final SocialUserRepository socialUserRepository;

    @Transactional
    public LoginResponseDto login(CreateSocialUserDto createSocialUserDto) {
        SocialUser user =
                socialUserRepository.findByEmail(createSocialUserDto.getEmail()).orElse(null);
        
        // 첫 로그인 시점 / 회원정보 작성 X
        if (user == null) {
            SocialUser temp = socialUserRepository.save(createSocialUserDto.toEntity(Role.GUEST));

            return LoginResponseDto
                    .builder()
                    .uuid(temp.getUuid())
                    .userType("SOCIAL")
                    .role(temp.getRole())
                    .build();
        }
        
        // 회원정보 작성 O
        if (createSocialUserDto.getUuid() != null) {
            user.changeRole(Role.USER);

            return LoginResponseDto
                    .builder()
                    .uuid(user.getUuid())
                    .userType("SOCIAL")
                    .role(user.getRole())
                    .build();
        }
        
        // 로그인
        return LoginResponseDto.builder()
                .uuid(user.getUuid())
                .role(user.getRole())
                .userType("SOCIAL")
                .build();
    }

    public SocialUser findByUuid(String uuid) {
        return socialUserRepository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원 입니다."));
    }

}
