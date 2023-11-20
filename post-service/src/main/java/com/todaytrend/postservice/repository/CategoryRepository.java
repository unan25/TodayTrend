package com.todaytrend.postservice.repository;

import com.todaytrend.postservice.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface CategoryRepository extends JpaRepository<Category,Long>{
    void deleteAllByPostId(Long postId);
    List<Category> findAllByPostId(Long postId);

//    List<Long> findByPostIdAndCategoryName(String categoryName);

}
