package com.todaytrend.postservice.post.dto.CRUD;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class responseMakePostDto {
    private String content;
    private List<String> hashTagList;
    private List<String> userTagList;
    private List<Long> categoryIdList;
}
