package com.todaytrend.authservice.repository;

import com.todaytrend.authservice.domain.LocalUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocalUserRepository extends JpaRepository<LocalUser, Long> {
    Optional<LocalUser> findByEmail(String email);
    Optional<LocalUser> findByLocalUserId(Long local_user_id);
    Optional<LocalUser> findByUuid(String uuid);
}
