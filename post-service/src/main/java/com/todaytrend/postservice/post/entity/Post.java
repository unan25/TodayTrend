package com.todaytrend.postservice.post.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "POST")
//@org.hibernate.annotations.Cache(region = "postCache", usage =
//        CacheConcurrencyStrategy.READ_WRITE)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    private String content;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
    private String userUuid;

    @Builder
    public Post(String content, String userUuid){
        this.content = content;
        this.userUuid = userUuid;
    }

    public void updatePostContent(String content){
        this.content = content;
    }
}
