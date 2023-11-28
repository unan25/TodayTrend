package com.todaytrend.authservice.dto;

import com.todaytrend.authservice.domain.SocialUser;
import com.todaytrend.authservice.domain.enum_.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateSocialUserDto {

    private String email;

    private String uuid;

    private Role role;

    public SocialUser toEntity() {
        return SocialUser
                .builder()
                .email(this.email)
                .uuid(UUID.randomUUID().toString())
                .role(Role.USER)
                .build();
    }

}
