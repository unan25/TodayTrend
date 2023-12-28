package com.todaytrend.postservice.post.dto;

import lombok.Data;

@Data
public class RequestUserPostDto {
    private String uuid;
    private int page;
    private int size;
}
