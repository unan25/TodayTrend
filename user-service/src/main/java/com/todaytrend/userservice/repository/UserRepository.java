package com.todaytrend.userservice.repository;

import com.todaytrend.userservice.domain.User;
import com.todaytrend.userservice.dto.ResponseImgAndNicknameDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u.uuid FROM User u WHERE u.nickname = :nickname")
    String findUuidByNickname(@Param("nickname") String nickname);

    @Query("SELECT new com.todaytrend.userservice.dto.ResponseImgAndNicknameDto(u.profile_image, u.nickname) FROM User u WHERE u.uuid = :UUID")
    List<ResponseImgAndNicknameDto> findImgAndNicknameByUuid(@Param("UUID") String UUID);

}
