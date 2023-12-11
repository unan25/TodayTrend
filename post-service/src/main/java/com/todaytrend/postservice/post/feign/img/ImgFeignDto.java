package com.todaytrend.postservice.post.feign.img;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImgFeignDto {
    private List<ResponseImageDto> data;
    private int page;
    private int totalPage;
    private int nextPage;
}
