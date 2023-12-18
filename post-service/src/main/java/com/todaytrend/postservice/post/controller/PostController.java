package com.todaytrend.postservice.post.controller;

import com.todaytrend.postservice.post.dto.CRUD.RequestUpdatePostDto;
import com.todaytrend.postservice.post.dto.CRUD.ResponseMakePostDto;
import com.todaytrend.postservice.post.dto.RequestCheckLikedDto;
import com.todaytrend.postservice.post.dto.RequestHashTagResultDto;
import com.todaytrend.postservice.post.dto.RequestMainDto;
import com.todaytrend.postservice.post.dto.main.RequestTabDto;
import com.todaytrend.postservice.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/post")
//@CrossOrigin("http://127.0.0.1:5500")
public class PostController {

    private final PostService postService;

//  info : mainPage 포스트 리스트
//  api : Post api/post/main @RequestBody
    @PostMapping("main")
    public ResponseEntity<?> chooseCategory(@RequestBody RequestMainDto requestMainDto){
        return new ResponseEntity<>(postService.postListCategory(requestMainDto),HttpStatus.OK);
    }

//  info : post 생성 (insert)
//  api : Post api/post @RequestBody
    @PostMapping("")
    public ResponseEntity<?> makePost(@RequestBody ResponseMakePostDto responseMakePostDto){
        return new ResponseEntity(postService.makePost(responseMakePostDto), HttpStatus.OK);
    }

//  info : post 수정 (update)
//  api : Put api/post @RequestBody
    @PutMapping("")
    public ResponseEntity<?> updatePost(@RequestBody RequestUpdatePostDto requestPostDto){
        return new ResponseEntity(postService.updatePost(requestPostDto), HttpStatus.OK);
    }

//  info : post 삭제 (delete)
//  api : Delete api/post @RequestParam
    @DeleteMapping("")
    public ResponseEntity<?> deletePost(@RequestParam("postId")Long postId){
        return new ResponseEntity(postService.removePost(postId),HttpStatus.OK);
    }

//  info : post 상세보기 (Read)
//  api : Get api/post/postdetail @RequestParam
    @GetMapping("postdetail")
    public ResponseEntity<?> findPost(@RequestParam("postId")Long postId){
        return new ResponseEntity(postService.findPost(postId),HttpStatus.OK);
    }

//    info : post 상세보기 하단 게시글 리스트
//  api : Post api/post/posts/detaillist @RequestBody
    @PostMapping("posts/detaillist")
    public ResponseEntity<?> recommendPostWithDetail(@RequestBody RequestCheckLikedDto requestCheckLikedDto){
        return new ResponseEntity(postService.detailPostsList(requestCheckLikedDto), HttpStatus.OK);
    }

//    info : 해당 post에서 선택한 카테고리 목록
//  api : Get api/post/category @RequestParam
    @GetMapping("category")
    public ResponseEntity<?> findPostCategory(@RequestParam("postId")Long postId){
        return new ResponseEntity(postService.findPostCategoryList(postId),HttpStatus.OK);
    }

//    info : post 좋아요 클릭 (insert/delete)
//  api : Put api/post/like @RequestBody
    @PutMapping("like")
    public ResponseEntity<?> clickLike(@RequestBody RequestCheckLikedDto requestCheckLikedDto){
        return new ResponseEntity(postService.clickLike(requestCheckLikedDto), HttpStatus.OK);
    }

//    info : post 좋아요 클릭 여부(T/F)
//  api : Post api/post/liked @RequestBody
    @PostMapping("liked")
    public ResponseEntity<?> checkLiked(@RequestBody RequestCheckLikedDto requestCheckLikedDto){
        return new ResponseEntity<>(postService.checkLiked(requestCheckLikedDto),HttpStatus.OK);
    }
    
//    info : 해당 post 좋아요 개수
//  api : Get api/post/likecnt @RequestParam
    @GetMapping("likecnt")
    public ResponseEntity<?> checkLikedCnt(@RequestParam("postId")Long postId){
        return new ResponseEntity<>(postService.checkLikeCnt(postId),HttpStatus.OK);
    }

//    info : 해당 post에 좋아요를 누른 user 리스트
//  api : Get api/post/likelist @RequestParam
    @GetMapping("likelist")
    public ResponseEntity<?> postLikeList(@RequestParam("postId") String postId){
        return new ResponseEntity<>(postService.postLikeList(Long.getLong(postId)),HttpStatus.OK);
    }

//    info : 한 유저가 좋아요를 누른 post 리스트
//  api : Get api/post/likeposts/{uuid} @PathVariable
    @GetMapping("likeposts/{uuid}")
    public ResponseEntity<?> userLikePost(@PathVariable("uuid") String uuid){
        return new ResponseEntity<>(postService.userLikePost(uuid), HttpStatus.OK);
    }

//    info : AdminCategory 리스트 제공
//  api : Get api/post/admincategorylist
    @GetMapping("admincategorylist")
    public ResponseEntity<?> adminCategoryListForMain(){
        return new ResponseEntity<>(postService.findAdminCategoryList(),HttpStatus.OK);
    }

//    info : hashTag 검색
//  api : Get api/post/hashtag @RequestParam
    @GetMapping("/hashtag")
    public ResponseEntity<?> hashTagList(@RequestParam("hashtag")String hashtag){
        return new ResponseEntity<>(postService.findhashTag(hashtag), HttpStatus.OK);
    }

//    todo : 명세서에 추가해야함
//    info : hashTag 검색 시 보여주는 포스트 리스트
//    api : Post api/post/hashtag @RequestBody
    @PostMapping("/search")
    public ResponseEntity<?> hahTagSearchList(@RequestBody RequestHashTagResultDto requestDto){
        return new ResponseEntity<>(postService.findhashTagList(requestDto),HttpStatus.OK);
    }

}