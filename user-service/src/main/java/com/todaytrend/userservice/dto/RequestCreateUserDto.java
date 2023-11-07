package com.todaytrend.userservice.dto;

import com.todaytrend.userservice.domain.User;
import com.todaytrend.userservice.domain.enum_.Gender;
import com.todaytrend.userservice.domain.enum_.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter @Setter
@AllArgsConstructor
@Builder
public class RequestCreateUserDto {

    @NotBlank(message = "이메일은 반드시 입력하여야 합니다.")
    @Email
    private String email;

    @NotBlank(message = "전화번호는 반드시 입력하여야 합니다.")
    @Pattern(regexp = "^01(?:0|1|[6-9])-\\d{3,4}-\\d{4}$", message = "유효한 전화번호를 입력해주세요.")
    private String phone;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String birth;

    @NotBlank(message = "이름은 반드시 입력하여야 합니다.")
    private String name;

    @NotBlank(message = "별명은 반드시 입력하여야 합니다.")
    private String nickname; //@nickname

    private String website;

    private String introduce;

    private String profile_image;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean active;

    private String uuid;

    public User toEntity() {

        return User.builder()
                .email(this.email)
                .phone(this.phone)
                .gender(this.gender)
                .name(this.name)
                .nickname(this.nickname)
                .website(this.website)
                .introduce(this.introduce)
                .profile_image(this.profile_image)
                .role(this.role)
                .active(this.active)
                .uuid(UUID.randomUUID().toString())
                .createAt(LocalDateTime.now())
                .birth(this.birth)
                .build();
    }
    // todo : Auth 만들어서 email, password 가져오기 -> response에 가져오기.
}
