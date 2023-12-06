package com.todaytrend.imageservice.dto.response;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ResponseImageListDto {

    private List<ResponseImageDto> data;
    private boolean hasNextPage;
    private int nextPageParam;
}
