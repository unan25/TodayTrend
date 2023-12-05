package com.todaytrend.postservice.comment.controller;

import com.todaytrend.postservice.comment.dto.request.RequestCommentDto;
import com.todaytrend.postservice.comment.dto.request.RequestCommentLikeDto;
import com.todaytrend.postservice.comment.dto.request.RequestDeleteCommentDto;
import com.todaytrend.postservice.comment.dto.response.ResponseCommentLikeDto;
import com.todaytrend.postservice.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/comments")
public class CommentController {
    private final CommentService commentService;

    @GetMapping("health-check")
    public String healthCheck() {
        return "Comment-service: Hello!!";
    }

    @PostMapping("")
    public ResponseEntity<?> createComment(@RequestBody RequestCommentDto requestCommentDto) {
        return new ResponseEntity<>(commentService.createComment(requestCommentDto), HttpStatus.CREATED);
    }

    @GetMapping("") //부모 댓글만 조회
    public ResponseEntity<?> findParentCommentByPostId(@RequestParam("postId") Long postId) {
        return new ResponseEntity<>(commentService.findParentCommentByPostId(postId), HttpStatus.OK);
    }
    @GetMapping("{commentId}") // 대댓글만 조회
    public ResponseEntity<?> findCommentByCommentId(@PathVariable Long commentId) {
        return new ResponseEntity<>(commentService.findCommentByCommentId(commentId), HttpStatus.OK);
    }
    @PostMapping("delete")
    public ResponseEntity<?> deleteComment(@RequestBody RequestDeleteCommentDto requestDeleteCommentDto) {
        return new ResponseEntity<>(commentService.deleteCommentByCommentId(requestDeleteCommentDto), HttpStatus.OK);
    }
    @DeleteMapping("{postId}") // Post 서버에 서비스 만 넣으면 끝
    public ResponseEntity<?> deleteCommentAll(@PathVariable Long postId) {
        return new ResponseEntity<>(commentService.deleteCommentByPostId(postId),HttpStatus.OK);
    }
    @PostMapping("like")
    public ResponseEntity<?> commentLike(@RequestBody RequestCommentLikeDto requestCommentLikeDto) {
        return new ResponseEntity<>(commentService.commentLike(requestCommentLikeDto) ,HttpStatus.OK);
    }
}
