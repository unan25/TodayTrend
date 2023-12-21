package com.todaytrend.userservice.repository;

import com.todaytrend.userservice.domain.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    Optional<Follow> findByFollowerIdAndFollowingId(String followerId, String followingId);

    Long countByFollowerId(String followerId);

    Long countByFollowingId(String followingId);

    @Query("SELECT f.followerId FROM Follow f WHERE f.followingId = :followingId")
    List<String> findFollowerIdsByFollowingId(@Param("followingId") String followingId);

    @Query("SELECT f.followingId FROM Follow f WHERE f.followerId = :followerId")
    List<String> findFollowingIdsByFollowerId(@Param("followerId") String followerId);
}
