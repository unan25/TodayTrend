package com.todaytrend.postservice.comment.dto.request;

import lombok.Data;

@Data
public class RequestDeleteCommentDto {

    private Long commentId;

    private String userUuid;


}
