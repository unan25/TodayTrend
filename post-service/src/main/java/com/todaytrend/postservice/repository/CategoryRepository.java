package com.todaytrend.postservice.repository;

import com.todaytrend.postservice.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface CategoryRepository extends JpaRepository<Category,Long>{
//    todo : distinct붙은거 다 로직 새로 짜야함
    void deleteAllByPostId(Long postId);
    List<Category> findAllByPostId(Long postId);

//    List<Long> findByPostIdAndCategoryName(String categoryName);

//    @Query(value = "SELECT DISTINCT c.postId FROM Category c WHERE c.adminCategoryId IN :categoryId ORDER BY c.postId DESC ")
    @Query(value = "SELECT c.postId FROM Category c WHERE c.adminCategoryId IN :categoryId ORDER BY c.postId DESC ")
    List<Long> findPostIdByAdminCategoryIdIn(List<Long> categoryId);

    List<Long> findAdminCategoryIdByPostId(Long postId);




}
