package com.todaytrend.postservice.post.dto;

import com.todaytrend.postservice.post.dto.main.ResponsePostDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ResponseUserPostDto {
    private List<ResponsePostDto> data;
    private int totalPage;
    private int page;
}
