package com.todaytrend.imageservice.dto.request;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Data
@Builder
public class RequestQueryDto {

    private int page;
    private int size;
    private List<Integer> categoryList;
    private String uuid;
    private int tab;
}
