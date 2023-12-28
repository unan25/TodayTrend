package com.todaytrend.postservice.post.repository;

import com.todaytrend.postservice.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface PostRepository extends JpaRepository<Post,Long>{
    void deleteAllByPostId(Long postId);

    @Query(value = "SELECT Post FROM Post p WHERE p.userUuid = :userUuid AND p.userUuid != :followUuid ORDER BY p.createdAt")
    List<Post> findAllByWhereUserUuidOrderByCreateAt(@Param("userUuid") String userUuid ,@Param("followUuid") String followUuid);

    @Query(value = "SELECT DISTINCT p.postId FROM Post p ORDER BY p.postId DESC")
    Page<Long> findPostIdBy(PageRequest pageRequest);

    @Query(value = "SELECT DISTINCT p.postId FROM Post p where p.userUuid in :followings ORDER BY p.postId DESC")
    Page<Long> findPostIdByUserUuidIn(@Param("followings") List<String> followings, PageRequest pageRequest);

    @Query(value = "SELECT DISTINCT p.postId FROM Post p WHERE p.userUuid IN :followings AND p.postId IN :postIds ORDER BY p.postId DESC ")
    List<Long> findPostIdByUserUuidInAndPostIdIn(@Param("followings") List<String> followings, @Param("postIds") List<Long> postIds);

    @Query(value = "SELECT p.userUuid FROM Post p WHERE p.postId = :postId")
    List<String> findUserUuidByPostId(@Param("postId") Long postId);

    @Query(value = "SELECT p.postId FROM Post p WHERE  p.userUuid = :userUuid")
    List<Long> findPostIdByUserUuid(@Param("userUuid") String userUuid);

    @Query(value = "SELECT p FROM Post p WHERE p.postId = :postId")
    Optional<Post> findByPostId(@Param("postId") Long postId);

    @Query("SELECT p " +
            "FROM Post p " +
            "WHERE p.postId IN :postId ")
    List<Post> findAllByPostIds(@Param("postId") List<Long> postId);

    List<Post> findAllByUserUuid(String uuid);

    Post findPostByPostId(Long postId);

    //유저 페이지
    @Query(value = "SELECT p.postId FROM Post p where p.userUuid =:userUuid ORDER BY p.createdAt desc ")
    Page<Long> findPostIdByUserUuidOrderByCreatedAtDesc(String userUuid, PageRequest pageRequest);

    Long countByUserUuid(String userUuid);
}