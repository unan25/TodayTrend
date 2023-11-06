package com.todaytrend.postservice.repository;

import com.todaytrend.postservice.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike,Integer>{
}
