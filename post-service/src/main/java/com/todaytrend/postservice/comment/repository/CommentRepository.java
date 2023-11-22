package com.todaytrend.postservice.comment.repository;

import com.todaytrend.postservice.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostId(Long postId);
    Comment findByCommentId(Long commentId);

    List<Comment> findByParentId(Long commentId);

    void deleteAllByPostId(Long postId);
}
