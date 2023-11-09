package com.todaytrend.postservice.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponsePostDto {//Post 상세페이지 정보 보내주기

    private Integer postId;
    private String content;
    private LocalDateTime updatedAt;
    private List<String> categoryList;
    private Integer likeCnt; //좋아요 수
    private boolean like; //본인의 좋아요 클릭 여부

}
