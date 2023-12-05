package com.todaytrend.postservice.post.service;

import com.todaytrend.postservice.post.dto.CRUD.*;
import com.todaytrend.postservice.post.dto.RequestCheckLikedDto;
import com.todaytrend.postservice.post.dto.ResponseCheckLikedDto;
import com.todaytrend.postservice.post.dto.ResponseCreatedPostDto;
import com.todaytrend.postservice.post.dto.ResponseDto;

import java.util.List;

public interface PostService {
    ResponseCreatedPostDto makePost(responseMakePostDto responseMakePostDto);

    ResponseDto removePost(Long postId);

    ResponsePostDetailDto findPost(Long postId);

    boolean clickLike(RequestCheckLikedDto requestCheckLikedDto);

    ResponsePostDetailDto updatePost(Long postId , requestUpdatePostDto requestUpdatePostDto);

    List<Long> recommendPostForMain(RequestPostListForMain requestPostListForMain);

    responseDetailPostsDto detailPostsList(String userUuid, Long postId);

    //AdminCategoryList제공
    List<selectedCategoryListDto> findAdminCategoryList();

    //해당 포스트 카테고리 불러오기
    List<selectedCategoryListDto> findPostCategoryList(Long postId);

    //좋아요 유저의 클릭 여부
    boolean checkLiked(RequestCheckLikedDto requestCheckLikedDto);
    
    //좋아요 개수
    Integer checkLikeCnt(RequestCheckLikedDto requestCheckLikedDto);

    List<String> postLikeList(Long postId);

    List<Long> userLikePost(String UUID);

}
