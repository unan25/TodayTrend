package com.todaytrend.postservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class PostLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userPostLikeId;

    private String userUuid;
    private Long postId;

    @Builder
    public PostLike(String userUuid, Long postId){
        this.userUuid = userUuid;
        this.postId = postId;
    }

}
