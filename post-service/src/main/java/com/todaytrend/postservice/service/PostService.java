package com.todaytrend.postservice.service;

import com.todaytrend.postservice.dto.RequestDeleteReadPostDto;
import com.todaytrend.postservice.dto.RequestPostDto;
import com.todaytrend.postservice.dto.RequestPostListForMain;
import com.todaytrend.postservice.dto.ResponsePostDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {
    Long makePost(/*RequestPostDto requestPostDto*/MultipartFile[] images, String userUuid, String content);

    boolean removePost(RequestDeleteReadPostDto requestDeletePostDto);

    ResponsePostDto findPost(RequestDeleteReadPostDto requestReadPostDto);

    String clickLike(RequestDeleteReadPostDto requestLikeDto);

    ResponsePostDto updatePost(RequestPostDto requestPostDto, Long postId);

    List<Long> recommendPostForMain(RequestPostListForMain requestPostListForMain);
}
