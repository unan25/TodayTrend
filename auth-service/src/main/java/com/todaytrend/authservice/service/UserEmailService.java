package com.todaytrend.authservice.service;

import com.todaytrend.authservice.domain.LocalUser;
import com.todaytrend.authservice.repository.LocalUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserEmailService {

    private final LocalUserRepository localUserRepository;

    // 임시 비밀번호 생성
    public String getTmpPassword() {
        char[] charSet = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
                'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
        String pwd = "";
        
        // 문자 배열 길이의 값을 랜덤으로 10개를 추출하여 조합
        int index = 0;
        for (int i = 0; i < 10; i++) {
            index = (int)(charSet.length * Math.random());
            pwd += charSet[index];
        }
        
        log.info("임시 비밀번호 생성");
        return pwd;
    }

    // 임시 비밀번호 업데이트
    public void updatePassword(String tmpPassword, String email) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        // 이메일 존재 유무 확인
        LocalUser localUser = localUserRepository.findByEmail(email)
                .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 이메일 입니다."));
        // 새로운 비밀번호 암호화
        String newPwd = bCryptPasswordEncoder.encode(tmpPassword);
        // 암호화된 newPwd로 DB 반영
        localUser.updatePassword(newPwd);
        log.info("임시 비밀번호 발급 완료");
    }
}
