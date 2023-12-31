package com.todaytrend.postservice.post.dto.CRUD;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestPostListForMain {

    private String userUuid;
    private Long tab;
    private List<Long> categoryList;
    private Long arrayTab;

}
