package com.todaytrend.authservice.service;

import com.todaytrend.authservice.domain.LocalUser;
import com.todaytrend.authservice.repository.LocalUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailService implements UserDetailsService {
    private final LocalUserRepository localUserRepository;

    @Override
    public LocalUser loadUserByUsername(String email) {
        return localUserRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException((email)));
    }
}
