package com.todaytrend.postservice.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Table(name = "HASHTAG")
public class HashTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hashtagId;

    private String hashtag;
    private Long postId;

    @Builder
    public HashTag(String hashtag, Long postId){
        this.hashtag = hashtag;
        this.postId = postId;
    }
}
