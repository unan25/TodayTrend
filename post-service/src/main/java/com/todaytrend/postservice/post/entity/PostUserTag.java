package com.todaytrend.postservice.post.entity;

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

    private String nickname;
    private Long postId;

    @Builder
    public PostUserTag(String nickname, Long postId){
        this.nickname = nickname;
        this.postId = postId;
    }

}
