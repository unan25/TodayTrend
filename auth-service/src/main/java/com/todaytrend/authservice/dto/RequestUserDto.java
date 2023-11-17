package com.todaytrend.authservice.dto;

import com.todaytrend.authservice.domain.LocalUser;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class RequestUserDto {

    @Email(message = "email 형식에 맞게 작성하여야 합니다.")
    @NotBlank(message = "email은 반드시 작성하여야 합니다.")
    private String email;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^&*()_+\\\\[\\\\]{};':\",.<>?/`~-])(?=.{8,}).*$"
    ,message = "특수 문자, 영어, 숫자가 각 1개 이상이 포함된 총 8자리 이상의 비밀번호를 작성하여야 합니다.")
    private String password;

    private String uuid;

    public LocalUser toEntity(){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        return LocalUser.builder()
                .email(this.email)
                .password(bCryptPasswordEncoder.encode(this.password))
                .uuid(UUID.randomUUID().toString())
                .build();
    }
}
