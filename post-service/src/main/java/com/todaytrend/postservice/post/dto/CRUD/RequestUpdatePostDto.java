package com.todaytrend.postservice.post.dto.CRUD;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestUpdatePostDto {
    private Long postId;
    private String content;
    private List<String> hashTagList;
    private List<String> userTagList;
    private List<Long> categoryIdList;
}
