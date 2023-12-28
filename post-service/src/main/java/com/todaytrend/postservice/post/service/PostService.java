package com.todaytrend.postservice.post.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.todaytrend.postservice.post.dto.*;
import com.todaytrend.postservice.post.dto.CRUD.*;
import com.todaytrend.postservice.post.dto.main.RequestTabDto;
import com.todaytrend.postservice.post.dto.main.ResponseTabDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PostService {
    ResponseCreatedPostDto makePost(ResponseMakePostDto responseMakePostDto) throws JsonProcessingException;

    ResponseDto removePost(Long postId);

    ResponsePostDetailDto findPost(Long postId);

    boolean clickLike(RequestCheckLikedDto requestCheckLikedDto) throws JsonProcessingException;

    ResponsePostDetailDto updatePost(RequestUpdatePostDto requestUpdatePostDto) throws JsonProcessingException;

    ResponseDetailPostsDto detailPostsList(Long postId);

    //AdminCategoryList제공
    List<selectedCategoryListDto> findAdminCategoryList();

    //해당 포스트 카테고리 불러오기
    List<selectedCategoryListDto> findPostCategoryList(Long postId);

    //좋아요 유저의 클릭 여부
    boolean checkLiked(RequestCheckLikedDto requestCheckLikedDto);
    
    //좋아요 개수
    Integer checkLikeCnt(Long postId);

    List<String> postLikeList(Long postId);

    List<Long> userLikePost(String UUID);


    ResponseTabDto postListCategory(/*List<Long> categoryIds*/ RequestMainDto requestMainDto);

//  hashTag 검색
    List<String> findhashTag(String hashTag);

    ResponseTabDto findhashTagList(RequestHashTagResultDto requestDto);

    ResponseUserPostDto userPostList(RequestUserPostDto requestUserPostDto);

    Long userPostCnt(String uuid);
}