package com.todaytrend.postservice.controller.api;

import com.todaytrend.postservice.Excepion.CustomException;
import com.todaytrend.postservice.Excepion.ErrorEnum;
import com.todaytrend.postservice.Excepion.ResponseDto;
import com.todaytrend.postservice.Excepion.SuccessEnum;
import com.todaytrend.postservice.dto.RequestDeleteReadPostDto;
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
@RequestMapping("test")
public class PostApiContoller {

    private final PostApiService postApiService;

    @GetMapping("")
    public ResponseEntity<?> testException() throws RuntimeException{
//        throw new CustomException(ErrorEnum.NO_RIGHT);
        return new ResponseEntity<>(new ResponseDto(SuccessEnum.OK,new RequestDeleteReadPostDto(1l,"test")),HttpStatus.OK);
    }
}