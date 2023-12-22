package com.todaytrend.postservice.comment.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.todaytrend.postservice.comment.dto.request.RequestCommentDto;
import com.todaytrend.postservice.comment.dto.request.RequestCommentLikeDto;
import com.todaytrend.postservice.comment.dto.request.RequestDeleteCommentDto;
import com.todaytrend.postservice.comment.dto.response.ResponseCommentLikeDto;
import com.todaytrend.postservice.comment.repository.CommentRepositoryImpl;
import com.todaytrend.postservice.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/post/comments")
public class CommentController {
    private final CommentService commentService;


    @GetMapping("health-check")
    public String healthCheck() {
        return "Comment-service: Hello!!";
    }

    @PostMapping("") // 댓글 등록
    public ResponseEntity<?> createComment(@RequestBody RequestCommentDto requestCommentDto) throws JsonProcessingException {
        // 메세지큐에 전달하기
        commentService.publishCreateCommentMessage(requestCommentDto);
        return ResponseEntity.ok("댓글 등록");
    }

    @GetMapping("") //부모 댓글만 조회 (좋아요순) + 내가쓰지않은로직추가 + uuid
    public ResponseEntity<?> findParentCommentByPostId(@RequestParam("postId") Long postId,
                                                       @RequestParam("page") int page,
                                                       @RequestParam("size") int size,
                                                       @RequestParam("uuid") String uuid) {
        return new ResponseEntity<>(commentService.findParentCommentByPostId(postId,page,size,uuid), HttpStatus.OK);
    }
    @GetMapping("reply") // 대댓글만 조회 (좋아요순)
    public ResponseEntity<?> findCommentByCommentId(@RequestParam("commentId") Long commentId,
                                                    @RequestParam("page") int page,
                                                    @RequestParam("size") int size) {
        return new ResponseEntity<>(commentService.findCommentByCommentId(commentId,page,size), HttpStatus.OK);
    }
    @GetMapping("{uuid}") // 게시물 기준 내가 쓴 부모 댓글 조회
    public ResponseEntity<?> findMyComment(@PathVariable String uuid,
            @RequestParam("postId") Long postId) {
        return new ResponseEntity<>(commentService.findMyComment(postId,uuid), HttpStatus.OK);
    }
    @PostMapping("delete") // 댓글 삭제
    public ResponseEntity<?> deleteComment(@RequestBody RequestDeleteCommentDto requestDeleteCommentDto) {
        return new ResponseEntity<>(commentService.deleteCommentByCommentId(requestDeleteCommentDto), HttpStatus.OK);
    }
    @DeleteMapping("{postId}") // Post 서버에 서비스 만 넣으면 끝
    public ResponseEntity<?> deleteCommentAll(@PathVariable Long postId) {
        return new ResponseEntity<>(commentService.deleteCommentByPostId(postId),HttpStatus.OK);
    }
    @PostMapping("like") // 좋아요 등록 및 삭제
    public ResponseEntity<?> commentLike(@RequestBody RequestCommentLikeDto requestCommentLikeDto) throws JsonProcessingException{
        commentService.publishCommentLikeMessage(requestCommentLikeDto);
        return ResponseEntity.ok("댓글 좋아요 메세지 보냄");
    }
    @GetMapping("like-cnt") // 좋아요 수 조회
    public ResponseEntity<?> getCommentLike(@RequestParam("commentId") Long commentId) {
        return new ResponseEntity<>(commentService.getLikeCount(commentId) , HttpStatus.OK);
    }
    @PostMapping("liked") // 좋아요 눌렀는지 조회
    public ResponseEntity<?> getLiked(@RequestBody RequestCommentLikeDto requestCommentLikeDto) {
        return new ResponseEntity<>(commentService.checkLike(requestCommentLikeDto), HttpStatus.OK);
    }
    @GetMapping("cnt") // 총 댓글 수 조회
    public ResponseEntity<?> getTotalCount(@RequestParam("postId") Long postId) {
        return new ResponseEntity<>(commentService.getTotalCount(postId), HttpStatus.OK);
    }
    @GetMapping("reply-cnt") // 대댓글 수 조회
    public ResponseEntity<?> getReplyCount(@RequestParam("commentId") Long commentId) {
        return new ResponseEntity<>(commentService.getReplyCount(commentId), HttpStatus.OK);
    }
//    @GetMapping("test") //부모 댓글만 조회 (좋아요순) + 내가쓰지않은로직추가 + uuid //테스트
//    public ResponseEntity<?> test(@RequestParam("postId") Long postId,
//                                                       @RequestParam("page") int page,
//                                                       @RequestParam("size") int size,
//                                                       @RequestParam("uuid") String uuid) {
//        return new ResponseEntity<>(commentRepository.test(postId,page,size,uuid), HttpStatus.OK);
//    }

}
