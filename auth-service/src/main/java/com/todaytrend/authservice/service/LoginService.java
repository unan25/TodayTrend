package com.todaytrend.authservice.service;

import com.todaytrend.authservice.domain.LocalUser;
import com.todaytrend.authservice.dto.RequestUserDto;
import com.todaytrend.authservice.repository.LocalUserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
//@AllArgsConstructor
@RequiredArgsConstructor
public class LoginService {

    private final LocalUserRepository localUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public String login(RequestUserDto requestUserDto) {
        LocalUser localUser = localUserRepository.findByEmail(requestUserDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재 하지않는 유저입니다."));

        if (!bCryptPasswordEncoder.matches(requestUserDto.getPassword(), localUser.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return localUser.getUuid();
    }

    public LocalUser findByLocalUserId(Long localUserId) {
        return localUserRepository.findByLocalUserId(localUserId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
    }


}
