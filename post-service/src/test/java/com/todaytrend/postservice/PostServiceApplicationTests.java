package com.todaytrend.postservice;

import com.todaytrend.postservice.post.dto.CRUD.RequestPostListForMain;
import com.todaytrend.postservice.post.entity.Category;
import com.todaytrend.postservice.post.repository.CategoryRepository;
import com.todaytrend.postservice.post.service.PostService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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

    @PersistenceContext
    EntityManager em;

    @Autowired
    private PostService postService;
    @Autowired
    private CategoryRepository categoryRepo;

    @BeforeEach
    void init(){
        categoryRepo.save(new Category(1l,1l));
        categoryRepo.save(new Category(3l,1l));
        categoryRepo.save(new Category(1l,2l));
        categoryRepo.save(new Category(2l,2l));
        categoryRepo.save(new Category(3l,2l));
        categoryRepo.save(new Category(10l,2l));
        categoryRepo.save(new Category(2l,3l));
    }

    @Test
    void recommendPostForMain_테스트() {
        List<Long> result =
                postService.recommendPostForMain(
                        new RequestPostListForMain(
                                "user1",1l, List.of(1l,2l),0l));

        List<Long> result2 =
                postService.recommendPostForMain(
                        new RequestPostListForMain(
                                "user1",1l, List.of(1l),0l));

        System.out.println("============");
        categoryRepo.findAll().forEach(c-> System.out.println(c.getCategoryId()+"   "+c.getAdminCategoryId()+"    "+c.getPostId()));
        System.out.println("============");

        System.out.println("-----------"+result2);
        assertThat(result).isEqualTo(List.of(2l,1l,3l));
    }

}
