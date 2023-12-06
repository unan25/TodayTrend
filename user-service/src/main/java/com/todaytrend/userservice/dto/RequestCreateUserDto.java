package com.todaytrend.userservice.dto;

import com.todaytrend.userservice.domain.User;
import com.todaytrend.userservice.domain.enum_.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
@Builder
public class RequestCreateUserDto {

    @NotBlank(message = "전화번호는 반드시 입력하여야 합니다.")
    @Pattern(regexp = "^01(?:0|1|[6-9])-\\d{3,4}-\\d{4}$", message = "유효한 전화번호를 입력해주세요.")
    @Column(unique = true)
    private String phone;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String birth;

    @NotBlank(message = "이름은 반드시 입력하여야 합니다.")
    private String name;

    @NotBlank(message = "별명은 반드시 입력하여야 합니다.")
    @Column(unique = true)
    private String nickname; //@nickname

    private String website;

    private String introduction;

    private String profileImage;

    private String uuid;

    public User toEntity() {
        return User.builder()
                .phone(this.phone)
                .gender(this.gender)
                .name(this.name)
                .nickname(this.nickname)
                .website(this.website)
                .introduction(this.introduction)
//                .profileImage(this.profileImage)
                .uuid(this.uuid) // 추후에 auth에서 받아올 예정 auth -> react -> user
                .createAt(LocalDateTime.now())
                .birth(this.birth)
                .build();
    }
}
