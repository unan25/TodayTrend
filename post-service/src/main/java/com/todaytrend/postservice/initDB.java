package com.todaytrend.postservice;

import com.todaytrend.postservice.post.entity.*;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

//@Component
@RequiredArgsConstructor
public class initDB {

    private final InitService initService;

    @PostConstruct
    public void init(){
        initService.adminCategory();
        initService.Post1();

    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    public static class InitService{
        private final EntityManager em;

        public void adminCategory(){
            AdminCategory adminCategory1 = new AdminCategory(1L,"adminCategory1");
            AdminCategory adminCategory2 = new AdminCategory(2L,"adminCategory2");
            AdminCategory adminCategory3 = new AdminCategory(3L,"adminCategory3");
            AdminCategory adminCategory4 = new AdminCategory(4L,"adminCategory4");
            AdminCategory adminCategory5 = new AdminCategory(5L,"adminCategory5");
            AdminCategory adminCategory6 = new AdminCategory(6L,"adminCategory6");
            AdminCategory adminCategory7 = new AdminCategory(7L,"adminCategory7");
            AdminCategory adminCategory8 = new AdminCategory(8L,"adminCategory8");

            em.persist(adminCategory1);
            em.persist(adminCategory2);
            em.persist(adminCategory3);
            em.persist(adminCategory4);
            em.persist(adminCategory5);
            em.persist(adminCategory6);
            em.persist(adminCategory7);
            em.persist(adminCategory8);
        }

        public void Post1(){
            Post post1 = new Post("content1","uuid1");

            Post post2 = new Post("#hashtag1 content2 @nickName1","uuid2");
            Post post3 = new Post("#hashtag1 content3","uuid3");
            Post post4 = new Post("content4 @nickName1","uuid4");
            Post post5 = new Post("content5","uuid5");
            Post post6 = new Post("content6 @nickName1","uuid6");
            Post post7 = new Post("content7","uuid7");
            Post post8 = new Post("content8 @nickName1","uuid8");

            em.persist(post1);
            em.persist(post2);
            em.persist(post3);
            em.persist(post4);
            em.persist(post5);
            em.persist(post6);
            em.persist(post7);
            em.persist(post8);

            Category category1 = Category.builder().adminCategoryId(1L).postId(post1.getPostId()).build();
            Category category2 = Category.builder().adminCategoryId(2L).postId(post1.getPostId()).build();

            HashTag hashTag1 = HashTag.builder().hashtag("hashtag1").postId(post2.getPostId()).build();
            HashTag hashTag2 = HashTag.builder().hashtag("hashtag1").postId(post3.getPostId()).build();

            PostLike postLike1 = PostLike.builder().postId(post1.getPostId()).userUuid("uuid1").build();
            PostLike postLike2 = PostLike.builder().postId(post2.getPostId()).userUuid("uuid1").build();

            PostUserTag postUserTag1 = PostUserTag.builder().postId(post2.getPostId()).nickname("nickName1").build();
            PostUserTag postUserTag2 = PostUserTag.builder().postId(post4.getPostId()).nickname("nickName1").build();
            PostUserTag postUserTag3 = PostUserTag.builder().postId(post6.getPostId()).nickname("nickName1").build();
            PostUserTag postUserTag4 = PostUserTag.builder().postId(post8.getPostId()).nickname("nickName1").build();

            em.persist(category1);
            em.persist(category2);

            em.persist(hashTag1);
            em.persist(hashTag2);

            em.persist(postLike1);
            em.persist(postLike2);

            em.persist(postUserTag1);
            em.persist(postUserTag2);
            em.persist(postUserTag3);
            em.persist(postUserTag4);
        }

    }
}