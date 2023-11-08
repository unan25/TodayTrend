package com.todaytrend.authservice.repository;

import com.todaytrend.authservice.domain.LocalUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<LocalUser, Long> {

}
