package com.todaytrend.postservice.post.dto.CRUD;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class responsePostDetailDto {
    private Long postId;
    private String profileImage;
    private String nickName;
    private String content;
    private LocalDateTime updateAt;
    private List<selectedCategoryListDto> categoryList;
    private Long likeCnt;
    private boolean liked;
    private boolean postOwner;
}
