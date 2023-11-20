package com.todaytrend.imageservice.repository;

import com.todaytrend.imageservice.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findImageByPostId(Long postId);
}
