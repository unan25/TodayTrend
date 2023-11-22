package com.todaytrend.postservice;

import com.todaytrend.postservice.entity.Category;
import com.todaytrend.postservice.entity.Post;
import com.todaytrend.postservice.enumulator.CategoryNames;
import com.todaytrend.postservice.repository.PostRepository;
import com.todaytrend.postservice.repository.PostRepositoryImpl;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
@Transactional
public class RecommendPostsTest {


    @Autowired
    private EntityManager em;

    @Autowired
    private PostRepository postRepository;

    @Test
    @Rollback(value = false)
    void findPostIdBy() {
        // 테스트에 필요한 데이터 생성
        Post post1 = Post.builder().content("Post 1").userUuid("user1").build();
        Post post2 = Post.builder().content("Post 2").userUuid("user2").build();
        em.persist(post1);
        em.persist(post2);

        Category category1 = Category.builder().categoryName(CategoryNames.CATEGORY1.name()).postId(post1.getPostId()).build();
        Category category2 = Category.builder().categoryName(CategoryNames.CATEGORY2.name()).postId(post2.getPostId()).build();

        em.persist(category1);
        em.persist(category2);

        // 테스트용 데이터
        String userUuid = "user1";
        List<String> followings = Arrays.asList("user2");
        Integer tab = 2;
        List<String> categoryList = Arrays.asList(CategoryNames.CATEGORY1.name());

        // 테스트 메서드 호출
        List<Long> result = postRepository.findPostIdBy(/*userUuid, followings, tab, */categoryList);

        System.out.println("Result: " + result);

        // 결과 검증
        assertThat(result).containsExactly(post1.getPostId());
    }

}
