package com.todaytrend.postservice.dto.CRUD;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class responseDetailPostsDto {
    private String title1;
    private String title2;
    private List<Long> postIdList1;
    private List<Long> postIdList2;
    private List<selectedCategoryListDto> categoryList;
}
