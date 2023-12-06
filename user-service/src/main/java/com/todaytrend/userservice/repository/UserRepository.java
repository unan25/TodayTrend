package com.todaytrend.userservice.repository;

import com.todaytrend.userservice.domain.User;
import com.todaytrend.userservice.dto.ResponseImgAndNicknameDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u.uuid FROM User u WHERE u.nickname = :nickname")
    String findUuidByNickname(@Param("nickname") String nickname);

    @Query("SELECT new com.todaytrend.userservice.dto.ResponseImgAndNicknameDto(u.profileImage, u.nickname) FROM User u WHERE u.uuid = :UUID")
    List<ResponseImgAndNicknameDto> findImgAndNicknameByUuid(@Param("UUID") String UUID);

    Optional<User> findByNickname(String nickname);

}
