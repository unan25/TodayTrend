package com.todaytrend.postservice.post.service.api;

import com.todaytrend.postservice.post.dto.CRUD.RequestUpdatePostDto;
import com.todaytrend.postservice.post.dto.CRUD.RecommendPostIdListDto;
import org.springframework.data.domain.Pageable;


public interface PostApiService {
    RecommendPostIdListDto recommendPosts(RequestUpdatePostDto.RequestRecommendPostIdDto requestRecommendPostId, Pageable pageable);
}
