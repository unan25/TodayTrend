package com.todaytrend.postservice.repository;

import com.todaytrend.postservice.entity.Category;
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
    List<Category> findAllByPostId(Long postId);

//    List<Long> findByPostIdAndCategoryName(String categoryName);

    //todo : OR -> AND
    @Query(value = "SELECT DISTINCT c.postId FROM Category c WHERE c.categoryName in :categoryName ORDER BY c.postId DESC ")
    List<Long> findPostIdByCategoryNameIn(@Param("categoryName") List<String> categoryName);

  /*  List<Long> countByCategoryNameIn(List<String> categoryName);

    default void finding(List<String> categoryName){

    }*/
}
