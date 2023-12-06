package com.todaytrend.postservice.post.repository;

import com.todaytrend.postservice.post.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface PostLikeRepository extends JpaRepository<PostLike,Long>{
    void deleteAllByPostId(Long postId);

    PostLike findByUserUuidAndPostId(String userUuid, Long postId);

    void deleteByUserUuidAndPostId(String userUuid, Long postId);

    Long countByPostId(Long postId);

    @Query("SELECT p.userUuid FROM PostLike p WHERE p.postId = :postId")
    List<String> findUuidByPostId(@Param("postId") Long postId);

    @Query("SELECT p.postId FROM PostLike p where p.userUuid = :UUID")
    List<Long> findPostIdByUserUuid(@Param("UUID") String UUID);

    @Query("SELECT p.postId FROM PostLike p")
    List<Long> findPostIdBy();
}
