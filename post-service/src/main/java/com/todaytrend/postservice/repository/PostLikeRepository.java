package com.todaytrend.postservice.repository;

import com.todaytrend.postservice.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface PostLikeRepository extends JpaRepository<PostLike,Integer>{
    void deleteAllByPostId(Integer postId);
    List<PostLike> findAllByPostId(Integer postId);
    PostLike findByUserUuidAndPostId(String userUuid, Integer postId);

    void deleteByUserUuidAndPostId(String userUuid, Integer postId);
}
