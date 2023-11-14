package com.todaytrend.imageservice.repository;

import com.todaytrend.imageservice.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
