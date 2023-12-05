package com.todaytrend.postservice.post.controller;

import com.todaytrend.postservice.post.dto.CRUD.requestUpdatePostDto;
import com.todaytrend.postservice.post.dto.CRUD.responseMakePostDto;
import com.todaytrend.postservice.post.dto.CRUD.RequestPostListForMain;
import com.todaytrend.postservice.post.dto.RequestCheckLikedDto;
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

    //게시물 생성
    @PostMapping("")
    public ResponseEntity<?> makePost(@RequestBody responseMakePostDto responseMakePostDto){
        return new ResponseEntity(postService.makePost(responseMakePostDto), HttpStatus.OK);
    }

    //게시물 삭제
    @DeleteMapping("")
    public ResponseEntity<?> deletePost(@RequestParam("postId")Long postId){
        return new ResponseEntity(postService.removePost(postId),HttpStatus.OK);
    }

    //게시물 상세보기
    @GetMapping("")
    public ResponseEntity<?> findPost(@RequestParam("postId")Long postId){
        return new ResponseEntity(postService.findPost(postId),HttpStatus.OK);
    }

    //해당 포스트에서 선택한 카테고리 목록
    @GetMapping("category")
    public ResponseEntity<?> findPostCategory(@RequestParam("postId")Long postId){
        return new ResponseEntity(postService.findPostCategoryList(postId),HttpStatus.OK);
    }

    //좋아요 클릭(insert/delete)
    @PutMapping("/like")
    public ResponseEntity<?> clickLike(@RequestBody RequestCheckLikedDto requestCheckLikedDto){
        return new ResponseEntity(postService.clickLike(requestCheckLikedDto), HttpStatus.OK);
    }

    //좋아요 클릭 여부 (true false)
    @GetMapping("/liked")
    public ResponseEntity<?> checkLiked(@RequestBody RequestCheckLikedDto requestCheckLikedDto){
        return new ResponseEntity<>(postService.checkLiked(requestCheckLikedDto),HttpStatus.OK);
    }
    
    //좋아요 개수
    @GetMapping("/likeCnt")
    public ResponseEntity<?> checkLikedCnt(@RequestBody RequestCheckLikedDto requestCheckLikedDto){
        return new ResponseEntity<>(postService.checkLikeCnt(requestCheckLikedDto),HttpStatus.OK);
    }

    //좋아요 누른 유저 리스트
    @GetMapping("/likeList")
    public ResponseEntity<?> postLikeList(@RequestHeader String postId){
        return new ResponseEntity<>(postService.postLikeList(Long.getLong(postId)),HttpStatus.OK);
    }

    //해당 유저가 좋아요 누른 리스트
    @GetMapping("/likePosts")
    public ResponseEntity<?> userLikePost(@RequestHeader String UUID){
        return new ResponseEntity<>(postService.userLikePost(UUID), HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<?> updatePost(@RequestParam("postId")Long postId, @RequestBody requestUpdatePostDto requestPostDto){
        return new ResponseEntity(postService.updatePost(postId, requestPostDto), HttpStatus.OK);
    }

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


//    AdminCategory 리스트 제공
    @GetMapping("adminCategoryList")
    public ResponseEntity<?> adminCategoryListForMain(){
        return new ResponseEntity<>(postService.findAdminCategoryList(),HttpStatus.OK);
    }

}