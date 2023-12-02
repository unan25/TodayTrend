package com.todaytrend.postservice.post.repository;

import com.todaytrend.postservice.post.entity.AdminCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface AdminCategoryRepository extends JpaRepository<AdminCategory, Long> {
    List<AdminCategory> findAllByAdminCategoryIdIn(List<Long> ids);
}
