package com.todaytrend.postservice.post.controller.api;

import com.todaytrend.postservice.post.service.api.PostApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("test")
public class PostApiContoller {

    private final PostApiService postApiService;

    @GetMapping("")
    public ResponseEntity<?> testException() throws RuntimeException{
//        throw new CustomException(ErrorEnum.NO_RIGHT);
//        return new ResponseEntity<>(new ResponseDto(SuccessEnum.OK,new RequestDeleteReadPostDto(1l,"test")),HttpStatus.OK);
        return null;
    }
}