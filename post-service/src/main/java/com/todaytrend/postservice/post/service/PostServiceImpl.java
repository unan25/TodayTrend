package com.todaytrend.postservice.post.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.todaytrend.postservice.post.dto.*;
import com.todaytrend.postservice.post.dto.CRUD.*;
import com.todaytrend.postservice.post.dto.main.ResponsePostDto;
import com.todaytrend.postservice.post.dto.main.ResponseTabDto;
import com.todaytrend.postservice.post.entity.*;
import com.todaytrend.postservice.post.feign.img.ImgFeignClient;
import com.todaytrend.postservice.post.feign.img.ImgFeignDto;
import com.todaytrend.postservice.post.feign.img.RequestImageListDto;
import com.todaytrend.postservice.post.feign.user.FollowUserVO;
import com.todaytrend.postservice.post.feign.user.UserFeignClient;
import com.todaytrend.postservice.post.feign.user.UserFeignDto;
import com.todaytrend.postservice.post.rabbitmq.PostLikeMessageDto;
import com.todaytrend.postservice.post.rabbitmq.PostProducer;
import com.todaytrend.postservice.post.rabbitmq.PostTagMessageDto;
import com.todaytrend.postservice.post.repository.*;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Service
@Transactional
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepo;
    private final CategoryRepository categoryRepo;
    private final PostLikeRepository postLikeRepo;
    private final PostUserTagRepository postUserTagRepo;
    private final HashTagRepository hashTagRepo;
    private final AdminCategoryRepository adminCategoryRepo;
    private final UserFeignClient userFeignClient;
    private final ImgFeignClient imgFeignClient;
    private final ObjectMapper objectMapper;
    private final PostProducer postProducer;

    public static final String CIRCUIT_BREAKER_NAME = "customCircuitBreaker";

//--------------------------- 포스트 생성 --------------------------------

    @Override
    public ResponseCreatedPostDto makePost(ResponseMakePostDto responseMakePostDto) throws JsonProcessingException {
        String userUuid = responseMakePostDto.getUuid();

//        1. post생성
        Post post = Post.builder()
                        .userUuid(userUuid)
                        .content(responseMakePostDto.getContent())
                        .build();

        Post resultPost = postRepo.save(post);
        Long postId = resultPost.getPostId();

//        2.해시태그 저장
        makeHashTag(responseMakePostDto.getHashTagList(),postId);

//        3. userTag저장
        makePostUserTag(responseMakePostDto.getUserTagList(),postId);

//        3. category에 저장
        makeCategory(responseMakePostDto.getCategoryIdList(),postId);

        return ResponseCreatedPostDto.builder()
                .postId(post.getPostId())
                .build();
    }

    //hashTag insert
    private void makeHashTag(List<String> hashTagList, Long postId){
        for(String hashTag : hashTagList){
            hashTagRepo.save(HashTag.builder()
                    .hashtag(hashTag)
                    .postId(postId)
                    .build());
        }
    }

    //PostUserTag insert
    private void makePostUserTag(List<String> checkUserTag, Long postId) throws JsonProcessingException {
        for (String nickName :checkUserTag){
            // 게시물 태그 알림 보내기

            PostTagMessageDto messageDto = PostTagMessageDto.builder()
                    .sender(postRepo.findPostByPostId(postId).getUserUuid())
                    .receiver(nickName)
                    .content(postRepo.findPostByPostId(postId).getContent())
                    .postId(postId)
                    .build();
            String message = objectMapper.writeValueAsString(messageDto);
            postProducer.sendNcPostTagMessage(message);

            postUserTagRepo.save(PostUserTag.builder()
                    .postId(postId)
                    .nickname(nickName)
                    .build());
        }
    }

    //Category insert
    private void makeCategory(List<Long> categoryList, Long postId){
        for(Long id : categoryList){
            categoryRepo.save(Category.builder().adminCategoryId(id).postId(postId).build());
        }
    }

//---------------------------------------------------------------------------

//--------------------------포스트 삭제----------------------------------------
    @Override
    public ResponseDto removePost(Long postId) {
            hashTagRepo.deleteAllByPostId(postId);
            postUserTagRepo.deleteAllByPostId(postId);
            postLikeRepo.deleteAllByPostId(postId);
            categoryRepo.deleteAllByPostId(postId);
            postRepo.deleteAllByPostId(postId);
            return ResponseDto.builder()
                    .check(true)
                    .build();
    }

//----------------------------------------------------------------------------

//----------------------------포스트 불러오기------------------------------------

    @Override
    public ResponsePostDetailDto findPost(Long postId) {

        Post post = postRepo.findById(postId).orElseThrow(()->new RuntimeException("post가 없음"));

        UserFeignDto imgAndNickname = userFeign(post.getUserUuid());
        ImgFeignDto imgFeignDto = imageFeign(postId);

        return ResponsePostDetailDto.builder()
                .postId(post.getPostId())
                .postUserUUID(post.getUserUuid())
                .profileImage(imgAndNickname.getProfileImage())
                .nickName(imgAndNickname.getNickname())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .postImgs(imgFeignDto.getImageUrlList())
                .build();
    }

//---------------------------해당 포스트 카테고리 리스트 불러오기-------------------------------------------------

    @Override
    public List<selectedCategoryListDto> findPostCategoryList(Long postId) {

        return adminCategoryRepo.findAllByAdminCategoryIdIn(categoryRepo.findAdminCategoryIdByPostId(postId)).stream()
                .filter(Objects::nonNull)
                .map(category -> new selectedCategoryListDto(category.getAdminCategoryId(),category.getAdminCategoryName())).toList();

    }

//-------------------------------포스트 좋아요 누르기------------------------------------
    @Override
    public boolean clickLike(RequestCheckLikedDto requestCheckLikedDto) throws JsonProcessingException {

        String userUuid = requestCheckLikedDto.getUuid();
        Long postId = requestCheckLikedDto.getPostId();

        if(postLikeRepo.findByUserUuidAndPostId(userUuid, postId) != null){
            postLikeRepo.deleteByUserUuidAndPostId(userUuid,postId);
            postLikeRepo.countByPostId(postId);
            return false;
        }else{
            PostLikeMessageDto messageDto = PostLikeMessageDto.builder()
                    .sender(userUuid)
                    .receiver(postRepo.findPostByPostId(postId).getUserUuid())
                    .content(postRepo.findPostByPostId(postId).getContent())
                    .postId(postId)
                    .build();
            String message = objectMapper.writeValueAsString(messageDto);
            postProducer.sendNcPostLikeMessage(message);

            postLikeRepo.save(PostLike.builder().userUuid(userUuid).postId(postId).build());
            postLikeRepo.countByPostId(postId);
            return true;
        }

    }

//------------------------------포스트 좋아요 클릭 갯수 및 클릭된 여부----------------------------------------------

    @Override
    public boolean checkLiked(RequestCheckLikedDto requestCheckLikedDto) {
        return postLikeRepo.findByUserUuidAndPostId(
                requestCheckLikedDto.getUuid(), requestCheckLikedDto.getPostId())
                != null;
    }

    @Override
    public Integer checkLikeCnt(Long postId) {
        return postLikeRepo.countByPostId(postId).intValue();
    }

    @Override
    public List<String> postLikeList(Long postId) {
        return postLikeRepo.findUuidByPostId(postId);
    }

    @Override
    public List<Long> userLikePost(String UUID) {
        return postLikeRepo.findPostIdByUserUuid(UUID);
    }

    //----------------------------포스트 업데이트------------------------------------
    @Override
    @Transactional
    public ResponsePostDetailDto updatePost(RequestUpdatePostDto requestUpdatePostDto) throws JsonProcessingException {

        Long postId = requestUpdatePostDto.getPostId();

        Post post = postRepo.findById(postId).orElseThrow(()->new RuntimeException("잘못된 게시물 업데이트 요청"));
            post.updatePostContent(requestUpdatePostDto.getContent());
            hashTagRepo.deleteAllByPostId(postId);
            postUserTagRepo.deleteAllByPostId(postId);
            categoryRepo.deleteAllByPostId(postId);

            makeHashTag(requestUpdatePostDto.getHashTagList(),postId);
            makePostUserTag(requestUpdatePostDto.getUserTagList(),postId);
            makeCategory(requestUpdatePostDto.getCategoryIdList(),postId);

            UserFeignDto imgAndNickname = userFeign(post.getUserUuid());
            ImgFeignDto imgFeignDto = imagesFeign(RequestImageListDto.builder().postIdList(List.of(post.getPostId())).build());

            return ResponsePostDetailDto.builder()
                    .postId(post.getPostId())
                    .postUserUUID(post.getUserUuid())
                    .profileImage(imgAndNickname.getProfileImage())
                    .nickName(imgAndNickname.getNickname())
                    .content(post.getContent())
                    .createdAt(post.getCreatedAt())
                    .postImgs(imgFeignDto.getData().get(0).getImageUrlList())
                    .build();

    }


//    ----------- // 게시물 상세 보기 하단 게시글 리스트--------------------
    @Override
    public ResponseDetailPostsDto detailPostsList(Long postId) {

        String title1 = "@Nickname 님의 게시물";
        String title2 = "@Nickname 님의 게시물과 비슷한 게시물";

        String uuid = postRepo.findUserUuidByPostId(postId).get(0);

        List<Long> categoryList = categoryRepo.findAdminCategoryIdByPostId(postId);

        List<Long> postIds = categoryRepo.findPostIdByAdminCategoryIdIn(categoryList,
                PageRequest.ofSize(6)).toList();

        List<Post> reccomendationByUser = postRepo.findAllByUserUuid(uuid);
        List<Post> reccomendationByCategory = postRepo.findAllByPostIds(postIds);

        List<ResponsePostDto> postList1 = new ArrayList<>();
        List<ResponsePostDto> postList2 = new ArrayList<>();

        reccomendationByUser.forEach(post -> {
           postList1.add(ResponsePostDto
                   .builder()
                   .postId(post.getPostId())
                   .imageUrl(imgFeignClient
                           .getImageByPostId(post.getPostId())
                           .getImageUrlList().stream().findFirst().orElse(null))
                   .build());
        });

        reccomendationByCategory.forEach(post -> {
            postList2.add(ResponsePostDto
                    .builder()
                    .postId(post.getPostId())
                    .imageUrl(imgFeignClient
                            .getImageByPostId(post.getPostId())
                            .getImageUrlList().stream().findFirst().orElse(null))
                    .build());
        });

        return ResponseDetailPostsDto.builder()
                .title1(title1)
                .title2(title2)
                .postList1(postList1)
                .postList2(postList2)
                .build();
    }

// ---------------- AdminCategoryList제공(main페이지에) -----------------

    @Override
    public List<selectedCategoryListDto> findAdminCategoryList() {
        List<selectedCategoryListDto> adminCategoryList= new ArrayList<>();

        for(AdminCategory category : adminCategoryRepo.findAll()){
            adminCategoryList.add(new selectedCategoryListDto(category.getAdminCategoryId(), category.getAdminCategoryName()));
        }

        return adminCategoryList;
    }


//-----------------  main 최신 + 카테고리 --------------------------
    @Override
    @Cacheable(value = "mainPostCache" ,key = "'tab:' + #requestMainDto.tab + '-page:' + " +
            "#requestMainDto.page + '-size:' + #requestMainDto.size + '-categories:' + " +
            "#requestMainDto.categoryList.hashCode()")
    public ResponseTabDto postListCategory(RequestMainDto requestMainDto) {
        String uuid = requestMainDto.getUuid();
        Integer page = requestMainDto.getPage();
        Integer size = requestMainDto.getSize();
        Integer tab = requestMainDto.getTab();
        List<Long> categoryIds = requestMainDto.getCategoryList();

        PageRequest pageRequest = PageRequest.of(page,size);

        switch (tab){
            case 0 -> {//최신

                if(!categoryIds.isEmpty()){
                    Page<Long> pageResult = categoryRepo.findPostIdByAdminCategoryIdIn(categoryIds, pageRequest);

                    ImgFeignDto imgResult = imagesFeign(RequestImageListDto.builder()
                            .postIdList(pageResult.getContent())
                            .build());

                    return ResponseTabDto.builder()
                            .data(imgResult.getData().stream().filter(Objects::nonNull)
                                            .map(e->new ResponsePostDto(e.getPostId(),e.getImageUrl()))
                                            .toList())
                            .totalPage(pageResult.getTotalPages())
                            .page(page)
                            .build();
                }else {
                    Page<Long> pageResult = postRepo.findPostIdBy(pageRequest);

                    ImgFeignDto imgResult = imagesFeign(RequestImageListDto.builder()
                            .postIdList(pageResult.getContent())
                            .build());

                    return ResponseTabDto.builder()
                            .data(imgResult.getData().stream().filter(Objects::nonNull)
                                    .map(e->new ResponsePostDto(e.getPostId(),e.getImageUrl()))
                                    .toList())
                            .totalPage(pageResult.getTotalPages())
                            .page(page)
                            .build();
                }
            }
            case 1 -> {//좋아요

                Page<Long> pageResult = postLikeRepo.findPostIdBy(pageRequest);

                ImgFeignDto imgResult = imagesFeign(RequestImageListDto.builder()
                        .postIdList(pageResult.getContent())
                        .build());

                return ResponseTabDto.builder()
                        .data(
                                imgResult.getData().stream().filter(Objects::nonNull)
                                        .map(e->new ResponsePostDto(e.getPostId(),e.getImageUrl()))
                                        .toList()
                        )
                        .page(page)
                        .totalPage(pageResult.getTotalPages())
                        .build();
            }
            case 2 -> {//팔로잉

                Page<Long> pageResult = postRepo.findPostIdByUserUuidIn(userFeignFollowList(uuid).stream().filter(Objects::nonNull)
                        .map(FollowUserVO::getUuid).toList(), pageRequest);

                ImgFeignDto imgResult = imagesFeign(RequestImageListDto.builder()
                        .postIdList(pageResult.getContent())
                        .build());

                return ResponseTabDto.builder()
                        .data(
                                imgResult.getData().stream().filter(Objects::nonNull)
                                        .map(e->new ResponsePostDto(e.getPostId(),e.getImageUrl()))
                                        .toList()
                        )
                        .page(page)
                        .totalPage(pageResult.getTotalPages())
                        .build();
            }
        }

       return null;
    }

//---------------- 해시태그 검색---------------
    @Override
    public List<String> findhashTag(String hashTag) {
        return hashTagRepo.keywordSlice(hashTag).stream()
                .filter(Objects::nonNull)
                .map(e->Normalizer.normalize(e,Normalizer.Form.NFC))
                .toList();
    }

    @Override
    public ResponseTabDto findhashTagList(RequestHashTagResultDto resultDto) {
        Page<Long> postIdByHashtag = hashTagRepo.findPostIdByHashtag(
                Normalizer.normalize(resultDto.getHashtag(), Normalizer.Form.NFD)
                , PageRequest.of(resultDto.getPage(), resultDto.getSize()));

        ImgFeignDto imgFeignDto = imagesFeign(RequestImageListDto.builder()
                        .postIdList(postIdByHashtag.getContent())
                .build());

        return ResponseTabDto.builder()
                .data(imgFeignDto.getData().stream()
                        .filter(Objects::nonNull)
                        .map(c->ResponsePostDto.builder()
                                .postId(c.getPostId())
                                .imageUrl(c.getImageUrl())
                                .build()
                        ).toList()
                )
                .page(resultDto.getPage())
                .totalPage(postIdByHashtag.getTotalPages())
                .build();
    }

//    ------------------------------

//    ---------------------------OpenFeign----------------

    @CircuitBreaker(name = CIRCUIT_BREAKER_NAME, fallbackMethod = "userFeignFollowListError")
    public List<FollowUserVO> userFeignFollowList(String uuid){
        return userFeignClient.followingList(uuid);
    }

    @CircuitBreaker(name = CIRCUIT_BREAKER_NAME, fallbackMethod = "userFeignError")
    public UserFeignDto userFeign(String uuid){
        return userFeignClient.findImgAndNickname(uuid);
    }

    @CircuitBreaker(name = CIRCUIT_BREAKER_NAME, fallbackMethod = "imgFeignError")
    public ImgFeignDto imagesFeign(RequestImageListDto requestImageListDto){
        return imgFeignClient.getImagesByPostIdList(requestImageListDto);
    }

    @CircuitBreaker(name = CIRCUIT_BREAKER_NAME, fallbackMethod = "imgFeignError")
    public ImgFeignDto imageFeign(Long postId){
        return imgFeignClient.getImageByPostId(postId);
    }

    public ImgFeignDto imgFeignError(Throwable t){
        return new ImgFeignDto();
    }

    public UserFeignDto userFeignError(Throwable t){
        return new UserFeignDto();
    }

    public List<FollowUserVO> userFeignFollowListError(Throwable t){
        return List.of(new FollowUserVO());
    }

 // --------------------- 유저 페이지 게시물 리스트 --------------------------
    @Override
    public ResponseUserPostDto userPostList(RequestUserPostDto requestUserPostDto) {
        String userUuid = requestUserPostDto.getUuid();
        int page = requestUserPostDto.getPage();
        int size = requestUserPostDto.getSize();
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Long> postIdList = postRepo.findPostIdByUserUuidOrderByCreatedAtDesc(userUuid, pageRequest);
        ImgFeignDto imgFeignDto = imagesFeign(RequestImageListDto.builder()
                .postIdList(postIdList.getContent())
                .build());

        return ResponseUserPostDto.builder()
                .data(imgFeignDto.getData().stream()
                        .filter(Objects::nonNull)
                        .map(c->ResponsePostDto.builder()
                                .postId(c.getPostId())
                                .imageUrl(c.getImageUrl())
                                .build()
                        ).toList()
                )
                .totalPage(postIdList.getTotalPages())
                .page(page)
                .build();
    }

    @Override
    public Long userPostCnt(String uuid) {
      return postRepo.countByUserUuid(uuid);
    }
}

