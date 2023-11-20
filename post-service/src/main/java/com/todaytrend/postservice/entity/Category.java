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

    private String categoryName;
    private Long postId;

    @Builder
    public Category(String categoryName, Long postId){
        this.categoryName = categoryName;
        this.postId = postId;
    }

    public void changeCategory(String categoryName){
        this.categoryName = categoryName;
    }
}
