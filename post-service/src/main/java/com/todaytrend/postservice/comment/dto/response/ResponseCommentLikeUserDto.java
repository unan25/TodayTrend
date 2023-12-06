package com.todaytrend.postservice.comment.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ResponseCommentLikeUserDto {
    private List<String> uuidList;
}
