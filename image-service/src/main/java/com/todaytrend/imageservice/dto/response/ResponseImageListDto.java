package com.todaytrend.imageservice.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ResponseImageListDto {

    private List<ResponseImageDto> data;
    private int page;
    private int totalPage;
    private int nextPage;
}
