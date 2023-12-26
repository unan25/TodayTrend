package com.todaytrend.imageservice.repository;

import com.todaytrend.imageservice.entity.Image;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {

    List<Image> findImagesByPostId(Long postId);

    void deleteImageByPostId(Long postId);

    Image findFirstByPostId(Long postId);
}
