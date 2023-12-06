package com.todaytrend.postservice.post.dto.main;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
//@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseTabDto {
    private List<Long> postIdList;
}
