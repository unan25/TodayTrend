package com.todaytrend.postservice.comment.repository;

import com.todaytrend.postservice.comment.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostId(Long postId);

    List<Comment> findByParentId(Long parentId);

    Comment findByCommentId(Long commentId);

    void deleteAllByPostId(Long postId);

    List<Comment> findByPostIdAndParentIdIsNull(Long postId);

    Long countByPostId(Long postId);
    Long countByParentId(Long commentId);

    // postId , uuid로 내가 쓴 부모댓글 찾기
    List<Comment> findByPostIdAndUuidAndParentIdIsNull(Long postId, String uuid);

    @Query(value = "SELECT c.uuid FROM Comment c WHERE c.commentId = :parentId")
    String findUuidByCommentId(@Param(value = "parentId") Long parentId);

}
