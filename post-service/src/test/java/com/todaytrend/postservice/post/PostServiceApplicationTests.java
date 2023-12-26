//package com.todaytrend.postservice.post;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.todaytrend.postservice.post.dto.CRUD.RequestUpdatePostDto;
//import com.todaytrend.postservice.post.dto.CRUD.ResponseMakePostDto;
//import com.todaytrend.postservice.post.dto.CRUD.ResponsePostDetailDto;
//import com.todaytrend.postservice.post.dto.RequestCheckLikedDto;
//import com.todaytrend.postservice.post.dto.ResponseCreatedPostDto;
//import com.todaytrend.postservice.post.entity.HashTag;
//import com.todaytrend.postservice.post.feign.user.UserFeignClient;
//import com.todaytrend.postservice.post.repository.*;
//import com.todaytrend.postservice.post.service.PostService;
//import com.todaytrend.postservice.post.service.PostServiceImpl;
//import jakarta.persistence.EntityManager;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
//
//@SpringBootTest
//@Transactional
//class PostServiceApplicationTests {
//
//    @Autowired
//    EntityManager em;
//    @Autowired
//    PostServiceImpl postService;
//    @Autowired
//    PostRepository postRepository;
//    @Autowired
//    PostUserTagRepository postUserTagRepository;
//    @Autowired
//    PostLikeRepository postLikeRepository;
//    @Autowired
//    HashTagRepository hashTagRepository;
//    @Autowired
//    CategoryRepository categoryRepository;
//    @Autowired
//    AdminCategoryRepository adminCategoryRepository;
//    @Autowired
//    UserFeignClient feignClient;
//
// /*   @Autowired
//    public PostServiceApplicationTests(
//            PostService postService,
//            PostRepository postRepository,
//            PostUserTagRepository postUserTagRepository,
//            PostLikeRepository postLikeRepository,
//            HashTagRepository hashTagRepository,
//            CategoryRepository categoryRepository,
//            AdminCategoryRepository adminCategoryRepository){
//        this.postService = postService;
//        this.postRepository = postRepository;
//        this.postUserTagRepository =  postUserTagRepository;
//        this.postLikeRepository = postLikeRepository;
//        this.hashTagRepository = hashTagRepository;
//        this.categoryRepository =categoryRepository;
//        this.adminCategoryRepository = adminCategoryRepository;
//    }*/
//
//
//    @BeforeEach
//    void init(){
//
//    }
//
//    @Test
//    void 테스트(){
//        hashTagRepository.save(HashTag.builder()
//                        .postId(1L)
//                        .hashtag("가나다라")
//                .build());
//
//        assertThat(hashTagRepository.keywordSlice("ㄱㄴㄷㄹ").size()).isEqualTo(1);
//
//    }
//
//    @Test
//    void fegin_test() {
//        System.out.println("---------------------------------------");
//        System.out.println(postService.imageFeign(3l));
//
//    }
//
//    @Test
//    void 게시물_생성_찾기_수정_삭제() throws JsonProcessingException {
//
//        ResponseCreatedPostDto responseCreatedPostDto = postService.makePost(new ResponseMakePostDto("uuid100", "@uuid1 #스트릿"
//                , List.of("스트릿"), List.of("uuid1"), List.of(1L, 3L)));
//
//        ResponsePostDetailDto post = postService.findPost(responseCreatedPostDto.getPostId());
//
//        assertThat(post.getPostUserUUID()).isEqualTo("uuid100");
//
////        ResponsePostDetailDto updatePost = postService.updatePost(post.getPostId(), new RequestUpdatePostDto("#스트릿", List.of("스트릿"), List.of(), List.of(1L, 3L)));
////
////        assertThat(post.getPostId()).isEqualTo(updatePost.getPostId());
////
////        assertThat(postService.removePost(updatePost.getPostId()).isCheck()).isTrue();
//    }
//
//    @Test
//    void 게시물_상세보기_및_하단_게시물_리스트(){
//        assertThat(postService.findPost(3L).getPostUserUUID()).isEqualTo("uuid1");
//
////        assertThat(postService.detailPostsList("uuid1",3L).getPostList1()).isEmpty();
////
//        /*    private String title1;
//    private String title2;
//    private List<Long> postIdList1;
//    private List<Long> postIdList2;
//    private List<selectedCategoryListDto> categoryList;
//    private String postUuid;*/
//    }
//
//    @Test
//    void 해시태그_검색(){
//        assertThat(postService.findhashTag("ㄱ").get(0)).isEqualTo("가나다");
//    }
//
//    @Test
//    void 관리자_카테고리_제공(){
//        System.out.println(postService.findAdminCategoryList());
//    }
//
//    @Test
//    void 좋아요() throws JsonProcessingException {
//
//        assertThat(postService.clickLike(RequestCheckLikedDto.builder()
//                        .postId(1L)
//                        .uuid("uuid100")
//                .build())).isTrue();
//
////        assertThat(postService.checkLiked("uuid100",1L)).isTrue();
//
//        assertThat(postService.checkLikeCnt(1L)).isEqualTo(1);
//
//        assertThat(postService.postLikeList(1L).size()).isEqualTo(1);
//
//        assertThat(postService.userLikePost("uuid100").size()).isEqualTo(1);
//    }
//
//}
