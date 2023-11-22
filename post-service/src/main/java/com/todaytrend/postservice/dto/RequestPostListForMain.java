package com.todaytrend.postservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestPostListForMain {

    private String userUuid;
    private Integer tab;
    private List<String> categoryList;

}
