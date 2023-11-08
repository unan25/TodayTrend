package com.todaytrend.postservice.repository;

import com.todaytrend.postservice.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface PostLikeRepository extends JpaRepository<PostLike,Integer>{
}
