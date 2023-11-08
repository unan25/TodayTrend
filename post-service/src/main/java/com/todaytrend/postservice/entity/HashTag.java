package com.todaytrend.postservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class HashTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer hashtagId;

    private String hashtag;
    private Integer postId;

    @Builder
    public HashTag(String hashtag, Integer postId){
        this.hashtag = hashtag;
        this.postId = postId;
    }
}
