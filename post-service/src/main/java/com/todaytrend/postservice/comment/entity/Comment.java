package com.todaytrend.postservice.comment.entity;

import com.todaytrend.postservice.comment.dto.response.ResponseCommentDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    private String content;

    private Long parentId;

    @CreatedDate
    private LocalDateTime createAt;

    private String uuid;
    private Long postId;

    public void updateContent(String content){
        this.content = content;
    }
}
