package com.todaytrend.postservice.repository;

import com.todaytrend.postservice.entity.AdminCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface AdminCategoryRepository extends JpaRepository<AdminCategory, Long> {
    List<AdminCategory> findAllByAdminCategoryIdIn(List<Long> ids);
}
