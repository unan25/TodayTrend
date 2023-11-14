package com.todaytrend.imageservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ItemInfoTag {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemInfoTagId;

    // 태그 url
    private String itemUrl;
    // 태그 name
    private String urlName;

    // 태그를 렌더링 할 초기 좌표값
    private Long locationX;
    private Long locationY;
}

