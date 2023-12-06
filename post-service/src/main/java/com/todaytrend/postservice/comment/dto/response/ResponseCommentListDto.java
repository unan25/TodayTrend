package com.todaytrend.postservice.comment.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class ResponseCommentListDto {

    private List<ResponseCommentDto> commentList;
    private int commentCount;

}
