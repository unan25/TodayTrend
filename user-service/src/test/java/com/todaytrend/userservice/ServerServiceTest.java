package com.todaytrend.userservice;

import com.todaytrend.userservice.domain.User;
import com.todaytrend.userservice.domain.enum_.Gender;
import com.todaytrend.userservice.dto.ResponseImgAndNicknameDto;
import com.todaytrend.userservice.repository.UserRepository;
import com.todaytrend.userservice.service.ServerService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
@Transactional
public class ServerServiceTest {

    @Autowired
    EntityManager em;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private ServerService serverService;

    @BeforeEach
    void init(){
        em.persist(userRepo.save(User.builder()
                .phone("010-1234-1234")
                .name("name1")
                .nickname("nickname1")
                .introduction("  dd  ")
                .birth("2023")
                .profileImage("img1")
                .gender(Gender.FEMALE)
                .website("dd")
                .uuid("uuid1")
                .build()));
    }

    @Test
    void Nickname으로UUID조회(){
        String nickname = "nickname1";
        assertThat(serverService.findUuid(nickname)).isEqualTo("uuid1");
    }

//    @Test
//    void uuid로Img와Nickname조회(){
//        String uuid = "uuid1";
//        assertThat(serverService.findImgAndNickname(uuid)).isEqualTo(new ResponseImgAndNicknameDto("img1","nickname1"));
//    }


}
