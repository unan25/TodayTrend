package com.todaytrend.imageservice.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class RequestImageListDto {
    private List<Long> postIdList;
}
