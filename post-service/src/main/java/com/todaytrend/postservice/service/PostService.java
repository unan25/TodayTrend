package com.todaytrend.postservice.service;

import com.todaytrend.postservice.dto.CRUD.requestUpdatePostDto;
import com.todaytrend.postservice.dto.CRUD.responseDetailPostsDto;
import com.todaytrend.postservice.dto.CRUD.responseMakePostDto;
import com.todaytrend.postservice.dto.CRUD.responsePostDetailDto;
import com.todaytrend.postservice.dto.RequestPostListForMain;

import java.util.List;

public interface PostService {
    Long makePost(String userUuid,responseMakePostDto responseMakePostDto);

    boolean removePost(String userUuid, Long postId);

    responsePostDetailDto findPost(String userUuid, Long postId);

    String clickLike(String userUuid, Long postId);

    responsePostDetailDto updatePost(String userUuid, Long postId ,requestUpdatePostDto requestUpdatePostDto);

    List<Long> recommendPostForMain(RequestPostListForMain requestPostListForMain);

    responseDetailPostsDto detailPostsList(String userUuid, Long postId);
}
