package com.todaytrend.postservice.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "CATEGORY")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;
    private Long adminCategoryId;
    private Long postId;

    @Builder
    public Category(Long adminCategoryId, Long postId){
        this.adminCategoryId = adminCategoryId;
        this.postId = postId;
    }

    public void changeCategory(Long adminCategoryId){
        this.adminCategoryId = adminCategoryId;
    }
}
