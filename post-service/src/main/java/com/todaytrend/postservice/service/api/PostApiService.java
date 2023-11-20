package com.todaytrend.postservice.service.api;

import com.todaytrend.postservice.dto.api.RecommendPostIdListDto;
import com.todaytrend.postservice.dto.api.RequestRecommendPostIdDto;
import org.springframework.data.domain.Pageable;


public interface PostApiService {

    RecommendPostIdListDto recommendPosts(RequestRecommendPostIdDto requestRecommendPostId, Pageable pageable);
}
