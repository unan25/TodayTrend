package com.todaytrend.postservice.comment.repository;

import com.todaytrend.postservice.comment.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentLikeRepository extends JpaRepository<CommentLike,Long> {
    CommentLike findByCommentIdAndUuid(Long commentId , String uuid);

    boolean existsByCommentIdAndUuid(Long commentId, String uuid);

    Long countByCommentId(Long commentId);

    @Query("SELECT cl.uuid FROM CommentLike cl WHERE cl.commentId = :commentId")
    List<String> findUuidByCommentId(Long commentId);
}
