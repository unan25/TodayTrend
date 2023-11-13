package com.todaytrend.postservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class PostUserTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userPostTagId;

    private String userUuid;
    private Long postId;

    @Builder
    public PostUserTag(String userUuid, Long postId){
        this.userUuid = userUuid;
        this.postId = postId;
    }

}
