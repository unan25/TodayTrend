package com.todaytrend.postservice.post.service;

import com.todaytrend.postservice.post.dto.CRUD.*;
import com.todaytrend.postservice.post.dto.RequestCheckLikedDto;
import com.todaytrend.postservice.post.dto.RequestMainDto;
import com.todaytrend.postservice.post.dto.ResponseCreatedPostDto;
import com.todaytrend.postservice.post.dto.ResponseDto;
import com.todaytrend.postservice.post.dto.main.RequestTabDto;
import com.todaytrend.postservice.post.dto.main.ResponseTabDto;

import java.util.List;

public interface PostService {
    ResponseCreatedPostDto makePost(ResponseMakePostDto responseMakePostDto);

    ResponseDto removePost(Long postId);

    ResponsePostDetailDto findPost(Long postId);

    boolean clickLike(RequestCheckLikedDto requestCheckLikedDto);

    ResponsePostDetailDto updatePost(Long postId , RequestUpdatePostDto requestUpdatePostDto);

    ResponseDetailPostsDto detailPostsList(String uuid, Long postId);

    //AdminCategoryList제공
    List<selectedCategoryListDto> findAdminCategoryList();

    //해당 포스트 카테고리 불러오기
    List<selectedCategoryListDto> findPostCategoryList(Long postId);

    //좋아요 유저의 클릭 여부
    boolean checkLiked(String uuid, Long postId);
    
    //좋아요 개수
    Integer checkLikeCnt(Long postId);

    List<String> postLikeList(Long postId);

    List<Long> userLikePost(String UUID);

    // /main chooseTab 최신, 좋아요, 팔로잉 순
    ResponseTabDto postListTab(Integer tab,String uuid, Integer page, Integer size);

//    main 최신 + 카테고리
    ResponseTabDto postListCategory(/*List<Long> categoryIds*/ RequestMainDto requestMainDto);

//  hashTag 검색
    List<String> findhashTag(String hashTag);

}