package com.todaytrend.postservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestDeleteReadPostDto { //exceptionx테스트용으로만 사용중
    private Long postId;
    private String userUuid;
}
