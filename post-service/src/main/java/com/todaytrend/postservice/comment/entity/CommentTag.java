package com.todaytrend.postservice.comment.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CommentTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentTagId ;

    private String nickname;

    private Long commentId;
}
