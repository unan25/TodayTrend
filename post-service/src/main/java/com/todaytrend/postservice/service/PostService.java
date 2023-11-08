package com.todaytrend.postservice.service;

import com.todaytrend.postservice.dto.RequestDeletePostDto;
import com.todaytrend.postservice.dto.RequestPostDto;

public interface PostService {
    String makePost(RequestPostDto requestPostDto);

    String removePost(RequestDeletePostDto requestDeletePostDto);
}
