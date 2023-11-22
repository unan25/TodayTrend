package com.todaytrend.postservice.comment.controller;

import com.todaytrend.postservice.comment.dto.request.RequestCommentDto;
import com.todaytrend.postservice.comment.dto.request.RequestDeleteCommentDto;
import com.todaytrend.postservice.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/comment")
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

    @GetMapping("{postId}")
    public ResponseEntity<?> findCommentByPostId(@PathVariable Long postId) {
        return new ResponseEntity<>(commentService.findCommentByPostId(postId), HttpStatus.OK);
    }
    @DeleteMapping()
    public ResponseEntity<?> deleteComment(@RequestBody RequestDeleteCommentDto requestDeleteCommentDto) {
        return new ResponseEntity<>(commentService.deleteCommentByCommentId(requestDeleteCommentDto), HttpStatus.OK);
    }
    @DeleteMapping("{postId}")
    public ResponseEntity<?> deleteCommentAll(@PathVariable Long postId) {
        return new ResponseEntity<>(commentService.deleteCommentByPostId(postId),HttpStatus.OK);
    }
}
