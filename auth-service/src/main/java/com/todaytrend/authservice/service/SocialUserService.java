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
        
        // DB에 Social 유저가 없다면 회원가입
        if (user == null) {
            SocialUser temp = socialUserRepository.save(createSocialUserDto.toEntity(Role.GUEST));
            return LoginResponseDto
                    .builder()
                    .uuid(temp.getUuid())
                    .userType("SOCIAL")
                    .role(temp.getRole())
                    .build();
        }
        
        // createSocialUserDto에 uuid가 있다면 UserInfo 작성x
        if (createSocialUserDto.getUuid() != null) {
            user.changeRole(Role.USER);
            return LoginResponseDto
                    .builder()
                    .uuid(user.getUuid())
                    .userType("SOCIAL")
                    .role(user.getRole())
                    .build();
        }
        
        // UserInfo 작성 후, 정상적인 회원가입 시 Role 변겨
        if (Role.GUEST == user.getRole()) {
            return LoginResponseDto
                    .builder()
                    .uuid(user.getUuid())
                    .userType("SOCIAL")
                    .role(user.getRole())
                    .build();
        }
        
        // Login 성공 시, LoginResponseDto 반환
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
