package com.todaytrend.postservice.post.service.api;

import com.todaytrend.postservice.post.dto.api.RecommendPostIdListDto;
import com.todaytrend.postservice.post.dto.api.RequestRecommendPostIdDto;
import org.springframework.data.domain.Pageable;


public interface PostApiService {

    RecommendPostIdListDto recommendPosts(RequestRecommendPostIdDto requestRecommendPostId, Pageable pageable);
}
