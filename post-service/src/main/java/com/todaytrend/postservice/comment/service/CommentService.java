package com.todaytrend.postservice.comment.service;

import com.todaytrend.postservice.comment.dto.request.RequestCommentDto;
import com.todaytrend.postservice.comment.dto.request.RequestDeleteCommentDto;
import com.todaytrend.postservice.comment.dto.response.ResponseCommentDto;
import com.todaytrend.postservice.comment.entity.Comment;
import com.todaytrend.postservice.comment.repository.CommentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    //---------------------------댓글 검증--------------------------
    //나의 댓글인지 확인하기 - 내꺼면 true , 아니면 false
    public boolean isMyComment(Comment comment, String userUuid){
        return comment.getUserUuid().equals(userUuid);
    }
    //대댓글인지 확인하기 - 대댓글이면 true , 아니면 false
    public boolean isReplyComment(Comment comment) {
        return comment.getParentId() != null;
    }
    //대댓글이 있는지 확인하기 - 있으면 true ,없으면 false
    public boolean hasReplyComments(Comment comment) {
        Long commentId = comment.getCommentId();
        List<Comment> byParentId = commentRepository.findByParentId(commentId);
        return !byParentId.isEmpty();
    }

    //---------------------------댓글 등록--------------------------
    //기본 댓글 등록
    public String createComment(RequestCommentDto requestCommentDto) {
        Comment comment = Comment.builder()
                .postId(requestCommentDto.getPostId())
                .content(requestCommentDto.getContent())
                .userUuid(requestCommentDto.getUserUuid())
                .parentId(requestCommentDto.getParentId())
                .createAt(LocalDateTime.now())
                .build();
        commentRepository.save(comment);

        return "댓글 저장 완료";
    }
    //---------------------------댓글 조회--------------------------
    // postId로 댓글 전체 조회
    public List<Comment> findCommentByPostId(Long postId) {
        return commentRepository.findByPostId(postId);
    }
    // commentId로 parent-comment 대댓글 조회
    public List<Comment> findCommentByCommentId(Long commentId) {
        return commentRepository.findByParentId(commentId);
    }

    //---------------------------댓글 삭제--------------------------

    // commentId로 선택한 댓글 삭제
    public String deleteCommentByCommentId(RequestDeleteCommentDto requestDeleteCommentDto) {
        String userUuid = requestDeleteCommentDto.getUserUuid();
        Long commentId = requestDeleteCommentDto.getCommentId();
        Comment comment = commentRepository.findByCommentId(commentId);

        // 1. 내가 쓴 댓글이고 , 대댓글이 아니고, 대댓글이 없으면 찐 삭제
        if(isMyComment(comment,userUuid) && !isReplyComment(comment) && !hasReplyComments(comment)) {
            commentRepository.delete(comment);
        }
        // 2. 내가 쓴 댓글이고, 대댓글이 아니고, 대댓글이 있으면 댓글 수정
        if (isMyComment(comment, userUuid) && !isReplyComment(comment) && hasReplyComments(comment)) {
            Comment.builder()
                    .content("삭제된 댓글입니다.")
                    .userUuid(null)
                    .build();
        }
        // 3. 내가 쓴 댓글이고, 대댓글이면 찐 삭제
        if(isMyComment(comment,userUuid) && isReplyComment(comment)) {
            commentRepository.delete(comment);
        }

        return "댓글 삭제 요청 완료";
    }

    //postId로 조회한 모든 댓글 삭제
    @Transactional
    public String deleteCommentByPostId(Long postId) {
        commentRepository.deleteAllByPostId(postId);
        return postId +"인 댓글 삭제 완료.";
    }
}
