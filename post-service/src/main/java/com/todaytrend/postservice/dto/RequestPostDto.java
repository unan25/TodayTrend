package com.todaytrend.postservice.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class RequestPostDto {//Post 입력 하기
    private String userUuid;
    private String content;
    private List<String> categoryList;
}
