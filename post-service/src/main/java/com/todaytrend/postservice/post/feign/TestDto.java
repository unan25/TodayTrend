package com.todaytrend.postservice.post.feign;

import lombok.Getter;

@Getter
public class TestDto {

    private Long id;
    private String text;

    public TestDto(){
        id = 1L;
        text = "testing";
    }

}
