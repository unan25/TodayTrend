package com.todaytrend.postservice.post.service.api;

import com.todaytrend.postservice.post.dto.CRUD.requestUpdatePostDto;
import com.todaytrend.postservice.post.dto.CRUD.RecommendPostIdListDto;
import com.todaytrend.postservice.post.entity.Post;
import com.todaytrend.postservice.post.repository.*;
import com.todaytrend.postservice.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostApiServiceImpl implements PostApiService {

    private final PostRepository postRepo;
    private final CategoryRepository categoryRepo;
    private final PostLikeRepository postLikeRepo;
    private final PostUserTagRepository postUserTagRepo;
    private final HashTagRepository hashTagRepo;

    @Override
    public RecommendPostIdListDto recommendPosts(requestUpdatePostDto.RequestRecommendPostIdDto requestRecommendPostId, Pageable pageable) {


        return null;
    }
}
