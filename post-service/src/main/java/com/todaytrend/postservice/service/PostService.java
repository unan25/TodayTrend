package com.todaytrend.postservice.service;

import com.todaytrend.postservice.dto.RequestPostDto;

public interface PostService {
    String makePost(RequestPostDto requestPostDto);
}
