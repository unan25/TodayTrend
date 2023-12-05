package com.todaytrend.postservice.comment.repository;

import com.todaytrend.postservice.comment.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentLikeRepository extends JpaRepository<CommentLike,Long> {
    CommentLike findByCommentIdAndUuid(Long commentId , String uuid);

    int countByCommentId(Long commentId);
}
