package com.todaytrend.authservice.repository;

import com.todaytrend.authservice.domain.SocialUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SocialUserRepository extends JpaRepository<SocialUser, Long> {

    Optional<SocialUser> findByEmail(String email);

    Optional<SocialUser> findByUuid(String uuid);

}
