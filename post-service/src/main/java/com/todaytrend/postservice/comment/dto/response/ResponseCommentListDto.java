package com.todaytrend.postservice.comment.dto.response;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class ResponseCommentListDto implements Serializable {

    private List<ResponseCommentDto> commentList;
    private boolean hasNext; //to do
}
