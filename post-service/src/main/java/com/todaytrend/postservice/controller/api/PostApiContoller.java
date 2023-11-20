package com.todaytrend.postservice.controller.api;

import com.todaytrend.postservice.dto.api.RequestRecommendPostIdDto;
import com.todaytrend.postservice.service.api.PostApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
//@RequestMapping("post/api")
public class PostApiContoller {

    private final PostApiService postApiService;

    @GetMapping("")//post추천
    public ResponseEntity<?> recommendAllPost(@RequestBody RequestRecommendPostIdDto requestRecommendPostId, Pageable pageable){
        return new ResponseEntity(postApiService.recommendPosts(requestRecommendPostId, pageable), HttpStatus.OK);
    }

}