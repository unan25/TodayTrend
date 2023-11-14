package com.todaytrend.postservice.repository;

import com.todaytrend.postservice.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface PostRepository extends JpaRepository<Post,Long>{
    void deleteAllByPostId(Long postId);
}
