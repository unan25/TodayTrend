package com.todaytrend.postservice.service;

import com.todaytrend.postservice.dto.RequestDeleteReadPostDto;
import com.todaytrend.postservice.dto.RequestPostDto;
import com.todaytrend.postservice.dto.ResponsePostDto;

public interface PostService {
    String makePost(RequestPostDto requestPostDto);

    String removePost(RequestDeleteReadPostDto requestDeletePostDto);

    ResponsePostDto findPost(RequestDeleteReadPostDto requestReadPostDto);
}
