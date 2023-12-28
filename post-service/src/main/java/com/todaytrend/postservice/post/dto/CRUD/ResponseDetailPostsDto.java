package com.todaytrend.postservice.post.dto.CRUD;

import com.todaytrend.postservice.post.dto.main.ResponsePostDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseDetailPostsDto {
    private String title1;
    private String title2;
    private List<ResponsePostDto> postList1;
    private List<ResponsePostDto> postList2;
}