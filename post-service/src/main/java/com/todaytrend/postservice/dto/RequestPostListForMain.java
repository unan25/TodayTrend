package com.todaytrend.postservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class RequestPostListForMain {

    private String userUuid;
    private Integer tab;
    private List<String> categoryList;

}
