package com.todaytrend.postservice.post.repository;

import com.todaytrend.postservice.post.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

//    @Query(value = "SELECT c.postId FROM Category c WHERE c.adminCategoryId IN :categoryId ORDER BY c.categoryId ASC ")
    @Query("SELECT DISTINCT c.postId " +
            "FROM Category c " +
            "WHERE c.adminCategoryId IN :categoryId " +
            "ORDER BY (SELECT COUNT(a.postId) " +
            "FROM Category a " +
            "WHERE a.postId = c.postId ) " +
            "DESC ")
    Page<Long> findPostIdByAdminCategoryIdIn(@Param("categoryId") List<Long> categoryId, PageRequest pageRequest);

    @Query(value = "SELECT c.adminCategoryId FROM Category c WHERE c.postId = :postId")
    List<Long> findAdminCategoryIdByPostId(@Param("postId") Long postId);

}
