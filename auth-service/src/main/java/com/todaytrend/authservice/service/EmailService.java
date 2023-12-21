package com.todaytrend.authservice.service;

import com.todaytrend.authservice.dto.MailDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender javaMailSender;
    
    private static final String title = "TodayTrend 임시 비밀번호 안내 이메일";
    private static final String message = "안녕하세요. TodayTrend 입니다. 임시 비밀번호 안내 메일입니다."
            + "\n" + "회원님의 임시 비밀번호는 아래와 같습니다. 로그인 후 반드시 비밀번호를 변경해주세요."
            + "\n" + "임시 비밀번호 : ";
    private static final String fromAddress = "T.TodayTrend@gmail.com";
    
    // 이메일 생성
    public MailDto createMail(String tmpPassword, String userEmail){
        MailDto mailDto = MailDto.builder()
                .toAddress(userEmail)
                .title(title)
                .message(message + tmpPassword)
                .fromAddress(fromAddress)
                .build();

        log.info("메일 생성 완료");
        return mailDto;
    }
    
    // 이메일 전송
    public void sendEmail(MailDto mailDto) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mailDto.getToAddress());
        mailMessage.setSubject(mailDto.getTitle());
        mailMessage.setText(mailDto.getMessage());
        mailMessage.setFrom(mailDto.getFromAddress());
        mailMessage.setReplyTo(mailDto.getFromAddress());
        
        javaMailSender.send(mailMessage);
        log.info("메일 전송 완료");
    }
    
}
