package com.todaytrend.postservice.repository;

import com.todaytrend.postservice.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface PostLikeRepository extends JpaRepository<PostLike,Long>{
    void deleteAllByPostId(Long postId);
    List<PostLike> findAllByPostId(Long postId);
    PostLike findByUserUuidAndPostId(String userUuid, Long postId);

    void deleteByUserUuidAndPostId(String userUuid, Long postId);

    Long countByPostId(Long postId);
}
