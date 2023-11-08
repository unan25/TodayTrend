package com.todaytrend.postservice.repository;

import com.todaytrend.postservice.entity.HashTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface HashTagRepository extends JpaRepository<HashTag,Integer>{
}
