package com.todaytrend.postservice.post.service.api;

import com.todaytrend.postservice.post.dto.CRUD.RecommendPostIdListDto;
import com.todaytrend.postservice.post.dto.CRUD.requestUpdatePostDto;
import com.todaytrend.postservice.post.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class PostApiServiceImpl implements PostApiService {

    private final PostRepository postRepo;
    private final CategoryRepository categoryRepo;
    private final PostLikeRepository postLikeRepo;
    private final PostUserTagRepository postUserTagRepo;
    private final HashTagRepository hashTagRepo;


    private final RestTemplate restTemplate;

    @Value("${server1.url}")
    private String server1Url;

    @Override
    public RecommendPostIdListDto recommendPosts(requestUpdatePostDto.RequestRecommendPostIdDto requestRecommendPostId, Pageable pageable) {

        return null;
    }

    public String testFeign(){
        String url = server1Url + "/server1/test";
        return restTemplate.getForEntity(url, String.class).getBody();
    }

}
