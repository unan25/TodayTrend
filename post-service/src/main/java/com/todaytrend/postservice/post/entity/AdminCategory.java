package com.todaytrend.postservice.post.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Table(name = "ADMINCATEGORY")
@Getter
@AllArgsConstructor
public class AdminCategory {
    @Id
    private Long adminCategoryId;
    private String adminCategoryName;
}
