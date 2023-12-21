package com.todaytrend.postservice.post.repository;

import com.todaytrend.postservice.post.entity.PostUserTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface PostUserTagRepository extends JpaRepository<PostUserTag,Long>{
    void deleteAllByPostId(Long postId);
}
