package com.todaytrend.postservice.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Table(name = "POSTUSERTAG")
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
