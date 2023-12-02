package com.todaytrend.postservice.post.controller;

import com.todaytrend.postservice.post.dto.CRUD.requestUpdatePostDto;
import com.todaytrend.postservice.post.dto.CRUD.responseMakePostDto;
import com.todaytrend.postservice.post.dto.RequestPostListForMain;
import com.todaytrend.postservice.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/post")
//@RequestMapping("post")
//@CrossOrigin("http://127.0.0.1:5500")
public class PostController {

    private final PostService postService;

    // Fixme : C )  완료 -ing
    @PostMapping("{UUID}")
    public ResponseEntity<?> makePost(@PathVariable("UUID")String userUuid, @RequestBody responseMakePostDto responseMakePostDto){
        return new ResponseEntity(postService.makePost(userUuid,responseMakePostDto), HttpStatus.OK);
    }

    // Fixme : D ) 완료 -ing
    @DeleteMapping("{UUID}")
    public ResponseEntity<?> deletePost(@PathVariable("UUID")String userUuid, @RequestParam("postId")Long postId){
        return new ResponseEntity(postService.removePost(userUuid, postId),HttpStatus.OK);
    }

    // Fixme : R ) 완료 -ing
    @GetMapping("{UUID}")
    public ResponseEntity<?> findPost(@PathVariable("UUID")String userUuid, @RequestParam("postId")Long postId){
        return new ResponseEntity(postService.findPost(userUuid,postId),HttpStatus.OK);
    }

    // Fixme : u ) 완료 -ing
    @PatchMapping("{UUID}")
    public ResponseEntity<?> clickLike(@PathVariable("UUID")String userUuid, @RequestParam("postId")Long postId){
        return new ResponseEntity(postService.clickLike(userUuid,postId), HttpStatus.OK);
    }

    // Fixme : U ) 완료 -ing
    @PutMapping("{UUID}")
    public ResponseEntity<?> updatePost(@PathVariable("UUID")String userUuid, @RequestParam("postId")Long postId, @RequestBody requestUpdatePostDto requestPostDto){
        return new ResponseEntity(postService.updatePost(userUuid, postId, requestPostDto), HttpStatus.OK);
    }

    // Fixme : r ) 완료 -ing
    // 게시물 상세 보기 하단 게시글 리스트
    @GetMapping("posts/detailList/{UUID}")
    public ResponseEntity<?> recommendPostWithDetail(@PathVariable("UUID")String userUuid, @RequestParam("postId")Long postId){
        return new ResponseEntity(postService.detailPostsList(userUuid,postId), HttpStatus.OK);
    }

//    --- sns Main에서 postid추천
//    Fixme : arrayTab , filter 해야함.
    @GetMapping("/main")
    public ResponseEntity<?> recommendPost(@RequestHeader("userUuid")String userUuid,
                                           @RequestHeader("tab")Long tab,
                                           @RequestHeader("categoryList") List<Long> categoryList,
                                           @RequestHeader("arrayTab")Long arrayTab ){
        RequestPostListForMain requestPostListForMain = new RequestPostListForMain(userUuid, tab, categoryList, arrayTab);
        return new ResponseEntity<>(postService.recommendPostForMain(requestPostListForMain),HttpStatus.OK);
    }

}