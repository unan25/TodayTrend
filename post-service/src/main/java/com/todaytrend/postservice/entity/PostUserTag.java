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
    private Integer userPostTagId;

    private String userUuid;
    private Integer postId;

    @Builder
    public PostUserTag(String userUuid, Integer postId){
        this.userUuid = userUuid;
        this.postId = postId;
    }

}
