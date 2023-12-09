package com.todaytrend.postservice.post;

import com.netflix.discovery.converters.Auto;
import com.todaytrend.postservice.post.dto.CRUD.RequestPostListForMain;
import com.todaytrend.postservice.post.dto.CRUD.ResponseMakePostDto;
import com.todaytrend.postservice.post.entity.*;
import com.todaytrend.postservice.post.feign.UserFeignClient;
import com.todaytrend.postservice.post.feign.UserFeignDto;
import com.todaytrend.postservice.post.repository.*;
import com.todaytrend.postservice.post.service.PostService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
@Transactional
class PostServiceApplicationTests {

    @Autowired
    EntityManager em;
    @Autowired
    PostService postService;
    @Autowired
    PostRepository postRepository;
    @Autowired
    PostUserTagRepository postUserTagRepository;
    @Autowired
    PostLikeRepository postLikeRepository;
    @Autowired
    HashTagRepository hashTagRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    AdminCategoryRepository adminCategoryRepository;
    @Autowired
    UserFeignClient feignClient;

 /*   @Autowired
    public PostServiceApplicationTests(
            PostService postService,
            PostRepository postRepository,
            PostUserTagRepository postUserTagRepository,
            PostLikeRepository postLikeRepository,
            HashTagRepository hashTagRepository,
            CategoryRepository categoryRepository,
            AdminCategoryRepository adminCategoryRepository){
        this.postService = postService;
        this.postRepository = postRepository;
        this.postUserTagRepository =  postUserTagRepository;
        this.postLikeRepository = postLikeRepository;
        this.hashTagRepository = hashTagRepository;
        this.categoryRepository =categoryRepository;
        this.adminCategoryRepository = adminCategoryRepository;
    }*/


    @BeforeEach
    void init(){

    }


    @Test
    void fegin_test() {
        System.out.println(feignClient.findImgAndNickname("user2"));
    }

    @Test
    void 게시물_생성(){
        assertThat(postService.makePost(new ResponseMakePostDto(
                "test_uuid","test_content",
                List.of(),List.of(),List.of(1L))).getPostId())
                .isEqualTo(postRepository.findPostIdByUserUuid("test_uuid").get(0));
    }



}
