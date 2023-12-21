package com.todaytrend.authservice.dto;

import com.todaytrend.authservice.domain.SocialUser;
import com.todaytrend.authservice.domain.enum_.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class CreateSocialUserDto {

    private String email;

    private String uuid;

    private Role role;

    public SocialUser toEntity(Role role) {
        return SocialUser
                .builder()
                .email(this.email)
                .uuid(UUID.randomUUID().toString())
                .role(role)
                .build();
    }

}
