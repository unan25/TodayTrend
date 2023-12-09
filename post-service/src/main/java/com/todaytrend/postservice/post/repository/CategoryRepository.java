package com.todaytrend.postservice.post.repository;

import com.todaytrend.postservice.post.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface CategoryRepository extends JpaRepository<Category,Long>{
    void deleteAllByPostId(Long postId);

    @Query(value = "SELECT c.postId FROM Category c WHERE c.adminCategoryId IN :categoryId ORDER BY c.categoryId ASC ")
    List<Long> findPostIdByAdminCategoryIdIn(List<Long> categoryId);

    @Query(value = "SELECT c.adminCategoryId FROM Category c WHERE c.postId = :postId")
    List<Long> findAdminCategoryIdByPostId(@Param("postId") Long postId);




}
