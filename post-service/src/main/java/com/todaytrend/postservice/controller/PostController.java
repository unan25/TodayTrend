package com.todaytrend.postservice.controller;

import com.todaytrend.postservice.dto.RequestDeleteReadPostDto;
import com.todaytrend.postservice.dto.RequestPostDto;
import com.todaytrend.postservice.dto.RequestPostListForMain;
import com.todaytrend.postservice.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/post")
//@RequestMapping("post")
//@CrossOrigin("http://127.0.0.1:5500")
public class PostController {

    private final PostService postService;

    @PostMapping("")
    public ResponseEntity<?> makePost(/*@RequestBody RequestPostDto requestPostDto*/@RequestPart(value = "images",required = false) MultipartFile[] images,
                                                                                    @RequestPart("UUID")String uuid, @RequestPart("content")String content){
        return new ResponseEntity(/*postService.makePost(requestPostDto)*/postService.makePost(images, uuid, content), HttpStatus.OK);
    }

    @DeleteMapping("")
    public ResponseEntity<?> deletePost(@RequestBody RequestDeleteReadPostDto requestDeletePostDto){
        return new ResponseEntity(postService.removePost(requestDeletePostDto),HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<?> findPost(@RequestBody RequestDeleteReadPostDto requestReadPostDto){
        return new ResponseEntity(postService.findPost(requestReadPostDto),HttpStatus.OK);
    }

    @PatchMapping("")
    public ResponseEntity<?> clickLike(@RequestBody RequestDeleteReadPostDto requestDeleteReadPostDto){
        return new ResponseEntity(postService.clickLike(requestDeleteReadPostDto), HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<?> updatePost(@RequestBody RequestPostDto requestPostDto, @RequestParam Long postId){
        return new ResponseEntity(postService.updatePost(requestPostDto, postId), HttpStatus.OK);
    }

//    --- sns Main에서 postid추천
    @GetMapping("/main")
    public ResponseEntity<?> recommendPost(@RequestHeader("userUuid")String userUuid,
                                           @RequestHeader("tab")String tab,
                                           @RequestHeader("categoryList") List<String> categoryList){
        RequestPostListForMain requestPostListForMain = new RequestPostListForMain(userUuid, Integer.parseInt(tab), categoryList);
        return new ResponseEntity<>(postService.recommendPostForMain(requestPostListForMain),HttpStatus.OK);
    }

}