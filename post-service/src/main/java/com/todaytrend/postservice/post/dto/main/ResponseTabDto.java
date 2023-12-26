package com.todaytrend.postservice.post.dto.main;

import com.todaytrend.postservice.post.dto.CRUD.selectedCategoryListDto;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
//@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseTabDto implements Serializable {
    private List<ResponsePostDto> data;
    private Integer totalPage;
    private Integer page;
}