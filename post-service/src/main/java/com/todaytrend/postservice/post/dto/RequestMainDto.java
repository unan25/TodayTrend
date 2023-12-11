package com.todaytrend.postservice.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestMainDto {
    private Integer tab;
    private String uuid;
    private List<Long> categoryList;
    private Integer page;
    private Integer size;
}
