package com.todaytrend.authservice.service;

import com.todaytrend.authservice.domain.LocalUser;
import com.todaytrend.authservice.domain.UserInterface;
import com.todaytrend.authservice.dto.LoginResponseDto;
import com.todaytrend.authservice.dto.RequestUserDto;
import com.todaytrend.authservice.dto.ResponseUserDto;
import com.todaytrend.authservice.repository.LocalUserRepository;
import com.todaytrend.authservice.repository.SocialUserRepository;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final LocalUserRepository localUserRepository;
    private final SocialUserRepository socialUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public ResponseUserDto createUser(RequestUserDto requestUserDto) {
        // 이메일 중복 체크
        if(localUserRepository.findByEmail(requestUserDto.getEmail()).isPresent())
            throw new IllegalArgumentException("이미 등록된 이메일 입니다.");

        LocalUser localUser = requestUserDto.toEntity();
        localUserRepository.save(localUser);

        return ResponseUserDto.builder()
                .userType("LOCAL")
                .uuid(localUser.getUuid())
                .build();
    }

    // Login
    public LoginResponseDto login(RequestUserDto requestUserDto) {
        // 사용자 정보 조회
        LocalUser localUser = localUserRepository.findByEmail(requestUserDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
        // 비밀번호 검증
        if (!bCryptPasswordEncoder.matches(requestUserDto.getPassword(), localUser.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        // active 상태 확인
        if(!localUser.isActive()){
            throw new IllegalArgumentException("회원 탈퇴한 유저입니다.");
        }

        return LoginResponseDto.builder()
                .uuid(localUser.getUuid())
                .role(localUser.getRole())
                .userType("LOCAL")
                .build();
    }

    // 로컬 유저용 회원 탈퇴 (소프트 딜리트 : active 상태 false)
    // 소셜 유저 회원 탈퇴는 고민 해야 될 듯. (Social 연동 해제 등의 방법)
    public void deactivateUser(String uuid, String password) {
        LocalUser localUser = localUserRepository.findByUuid(uuid)
                .orElseThrow(()-> new NotFoundException("존재하지 않는 유저입니다."));
        if (!bCryptPasswordEncoder.matches(password, localUser.getPassword())) {
            throw new IllegalArgumentException("패스워드가 불일치 합니다.");
        }

        localUser.deactivate();
        localUserRepository.save(localUser);
    }

    // 이메일 중복 체크
    public boolean isEmailDuplicated(String email){
        return socialUserRepository.findByEmail(email).isPresent()
                || localUserRepository.findByEmail(email).isPresent();
    }

    public LocalUser findByUuid(String uuid) {
        return localUserRepository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원 입니다."));
    }


    public LocalUser findByLocalUserId(Long localUserId) {
        return localUserRepository.findByLocalUserId(localUserId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
    }
}
