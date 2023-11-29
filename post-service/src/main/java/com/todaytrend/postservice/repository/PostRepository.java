package com.todaytrend.postservice.repository;

import com.todaytrend.postservice.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface PostRepository extends JpaRepository<Post,Long>, CustomPostRepository{
    void deleteAllByPostId(Long postId);

     List<Post> findAllByOrderByUpdatedAt();

    @Query(value = "SELECT Post FROM Post p WHERE p.userUuid = :userUuid AND p.userUuid != :followUuid ORDER BY p.createAt")
    List<Post> findAllByWhereUserUuidOrderByCreateAt(@Param("userUuid") String userUuid ,@Param("followUuid") String followUuid);

    @Query(value = "SELECT DISTINCT p.postId FROM Post p ORDER BY p.postId DESC")
    List<Long> findPostIdBy();

    @Query(value = "SELECT DISTINCT p.postId FROM Post p where p.userUuid in :followings ORDER BY p.postId DESC")
    List<Long> findPostIdByUserUuidIn(@Param("followings") List<String> followings);

    @Query(value = "SELECT DISTINCT p.postId FROM Post p WHERE p.userUuid IN :followings AND p.postId IN :postIds ORDER BY p.postId DESC ")
    List<Long> findPostIdByUserUuidInAndPostIdIn(@Param("followings") List<String> followings, @Param("postIds") List<Long> postIds);

    String findUserUuidByPostId(Long postId);

    Long countByUserUuid(String userUuid);

    List<Long> findPostIdByUserUuid(String userUuid);

    Post findByPostId(Long postId);
}
