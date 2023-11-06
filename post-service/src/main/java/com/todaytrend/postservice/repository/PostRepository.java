package com.todaytrend.postservice.repository;

import com.todaytrend.postservice.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Integer>{
}
