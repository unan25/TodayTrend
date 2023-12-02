package com.todaytrend.postservice.post.service;

import com.todaytrend.postservice.post.dto.CRUD.requestUpdatePostDto;
import com.todaytrend.postservice.post.dto.CRUD.responseDetailPostsDto;
import com.todaytrend.postservice.post.dto.CRUD.responseMakePostDto;
import com.todaytrend.postservice.post.dto.CRUD.responsePostDetailDto;
import com.todaytrend.postservice.post.dto.RequestPostListForMain;

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
