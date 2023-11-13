package com.todaytrend.postservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestDeleteReadPostDto { // POST 지울 때 받아오는 값
    private Long postId;
    private String userUuid;
}
