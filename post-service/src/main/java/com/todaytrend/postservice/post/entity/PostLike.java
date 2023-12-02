package com.todaytrend.postservice.post.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "POSTLIKE")
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
