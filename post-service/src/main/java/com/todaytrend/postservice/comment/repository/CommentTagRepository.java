package com.todaytrend.postservice.comment.repository;

import com.todaytrend.postservice.comment.entity.CommentTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentTagRepository extends JpaRepository<CommentTag, Long> {

}
