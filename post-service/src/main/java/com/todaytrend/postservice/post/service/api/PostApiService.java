package com.todaytrend.postservice.post.service.api;

import com.todaytrend.postservice.post.dto.CRUD.requestUpdatePostDto;
import com.todaytrend.postservice.post.dto.CRUD.RecommendPostIdListDto;
import org.springframework.data.domain.Pageable;


public interface PostApiService {
    RecommendPostIdListDto recommendPosts(requestUpdatePostDto.RequestRecommendPostIdDto requestRecommendPostId, Pageable pageable);
}
