package com.todaytrend.postservice.post.repository;

import com.todaytrend.postservice.post.entity.HashTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface HashTagRepository extends JpaRepository<HashTag,Long>{

    void deleteAllByPostId(Long postId);

}
