package com.todaytrend.postservice.controller;

import com.todaytrend.postservice.dto.RequestDeleteReadPostDto;
import com.todaytrend.postservice.dto.RequestPostDto;
import com.todaytrend.postservice.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("post")
//@CrossOrigin("http://127.0.0.1:5500")
public class PostController {

    private final PostService postService;

    @PostMapping("")
    public ResponseEntity<?> makePost(@RequestBody RequestPostDto requestPostDto){
        return new ResponseEntity(postService.makePost(requestPostDto), HttpStatus.OK);
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

}