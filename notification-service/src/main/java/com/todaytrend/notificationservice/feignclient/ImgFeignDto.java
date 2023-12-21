package com.todaytrend.notificationservice.feignclient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImgFeignDto {
    private Long postId;
    private List<String> imageUrlList;
}
