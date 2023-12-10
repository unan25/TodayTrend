package com.todaytrend.postservice.post.service;

import com.todaytrend.postservice.post.dto.CRUD.*;
import com.todaytrend.postservice.post.dto.CRUD.RequestPostListForMain;
import com.todaytrend.postservice.post.dto.RequestCheckLikedDto;
import com.todaytrend.postservice.post.dto.ResponseCreatedPostDto;
import com.todaytrend.postservice.post.dto.ResponseDto;
import com.todaytrend.postservice.post.dto.main.RequestTabDto;
import com.todaytrend.postservice.post.dto.main.ResponsePostDto;
import com.todaytrend.postservice.post.dto.main.ResponseTabDto;
import com.todaytrend.postservice.post.entity.*;
import com.todaytrend.postservice.post.feign.UserFeignClient;
import com.todaytrend.postservice.post.feign.UserFeignDto;
import com.todaytrend.postservice.post.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.text.Normalizer;
import java.util.*;
import java.util.stream.Collectors;

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
    
//    todo : 1. image 서버에 postid보내면 해당 포스트 img들 받아오기
//    todo : 2. image 서버에 List<Long> postId 보내면 첫번째 img list형태로 받아오기

//--------------------------- 포스트 생성 --------------------------------
    @Override
    public ResponseCreatedPostDto makePost(ResponseMakePostDto responseMakePostDto) {
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
    private void makePostUserTag(List<String> checkUserTag, Long postId){
        for (String nickName :checkUserTag){
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

        UserFeignDto imgAndNickname = userFeignClient.findImgAndNickname(post.getUserUuid());

        return ResponsePostDetailDto.builder()
                .postId(post.getPostId())
                .postUserUUID(post.getUserUuid())
                .profileImage(imgAndNickname.getProfileImage())
                .nickName(imgAndNickname.getNickname())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .postImgs(List.of())//todo:
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
    public boolean clickLike(RequestCheckLikedDto requestCheckLikedDto) {

        String userUuid = requestCheckLikedDto.getUuid();
        Long postId = requestCheckLikedDto.getPostId();

        if(postLikeRepo.findByUserUuidAndPostId(userUuid, postId) != null){
            postLikeRepo.deleteByUserUuidAndPostId(userUuid,postId);
            postLikeRepo.countByPostId(postId);
            return false;
        }else{
            postLikeRepo.save(PostLike.builder().userUuid(userUuid).postId(postId).build());
            postLikeRepo.countByPostId(postId);
            return true;
        }

    }

//------------------------------포스트 좋아요 클릭 갯수 및 클릭된 여부----------------------------------------------

    @Override
    public boolean checkLiked(String uuid, Long postId) {
        return postLikeRepo.findByUserUuidAndPostId(
                uuid, postId)
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
    public ResponsePostDetailDto updatePost( Long postId, RequestUpdatePostDto requestUpdatePostDto) {

            Post post = postRepo.findById(postId).orElseThrow(()->new RuntimeException("잘못된 게시물 업데이트 요청"));
            post.updatePostContent(requestUpdatePostDto.getContent());
            hashTagRepo.deleteAllByPostId(postId);
            postUserTagRepo.deleteAllByPostId(postId);
            categoryRepo.deleteAllByPostId(postId);

            makeHashTag(requestUpdatePostDto.getHashTagList(),postId);
            makePostUserTag(requestUpdatePostDto.getUserTagList(),postId);
            makeCategory(requestUpdatePostDto.getCategoryIdList(),postId);

            UserFeignDto imgAndNickname = userFeignClient.findImgAndNickname(post.getUserUuid());

            return ResponsePostDetailDto.builder()
                    .postId(post.getPostId())
                    .postUserUUID(post.getUserUuid())
                    .profileImage(imgAndNickname.getProfileImage())
                    .nickName(imgAndNickname.getNickname())
                    .content(post.getContent())
                    .createdAt(post.getCreatedAt())
                    .postImgs(List.of())//todo:
                    .build();

    }

//----------------------------메인 페이지에서 post 추천-----------------------------------------

    public List<Long> postIdList(List<Long> categoryList){
        Map<Long, Long> frequencyMap = categoryRepo.findPostIdByAdminCategoryIdIn(categoryList).stream().filter(Objects::nonNull)
                .collect(Collectors.groupingBy(e -> e, Collectors.counting()));

        return frequencyMap.entrySet().stream()
                .sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

//----------------------------------------------------------
//    ----------- // 게시물 상세 보기 하단 게시글 리스트--------------------
    @Override
    public ResponseDetailPostsDto detailPostsList(RequestCheckLikedDto requestDto) {

        Long postId = requestDto.getPostId();

        String title1 = "@Nickname 님의 게시물";
        String title2 = "@Nickname 님의 게시물과 비슷한 게시물";

        Post post = postRepo.findByPostId(postId);

        List<Long> postIdList1 = postRepo.findPostIdByUserUuid(postRepo.findUserUuidByPostId(postId).get(0));
        List<Long> postIdList2 = categoryRepo.findPostIdByAdminCategoryIdIn(categoryRepo.findAdminCategoryIdByPostId(postId));

        List<ResponsePostDto> postList1 = new ArrayList<>();
        List<ResponsePostDto> postList2 = new ArrayList<>();

        postIdList1.stream().filter(Objects::nonNull)
                .forEach(id -> postList1.add(new ResponsePostDto(id,null)));
        postIdList2.stream().filter(Objects::nonNull)
                .forEach(id -> postList2.add(new ResponsePostDto(id,null)));

        List<selectedCategoryListDto> categoryList = new ArrayList<>();
        for (AdminCategory adminCategory : adminCategoryRepo.findAllByAdminCategoryIdIn(categoryRepo.findAdminCategoryIdByPostId(postId))) {
            categoryList.add(new selectedCategoryListDto(adminCategory.getAdminCategoryId(), adminCategory.getAdminCategoryName()));
        }

        return ResponseDetailPostsDto.builder()
                .title1(title1)
                .title2(title2)
                .postList1(postList1)
                .postList2(postList2)
                .categoryList(categoryList)
                .postUuid(post.getUserUuid())
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

// -------------------main  chooseTab 최신, 좋아요, 팔로잉 순

    @Override
    public ResponseTabDto postListTab(Integer tab, String uuid, Integer page, Integer size) {
        ResponseTabDto responseTabDto = new ResponseTabDto();

        PageRequest pageRequest = PageRequest.of(page,size);

        switch (tab){
            case 0 -> {//최신
                return ResponseTabDto.builder()
                                .postList(
                                        postRepo.findPostIdBy(pageRequest).getContent()
                                                        .stream().filter(Objects::nonNull)
                                                        .map(e -> ResponsePostDto.builder()
                                                                .postId(e)
                                                                .postImg(null)
                                                                .build()
                                                        ).toList()
                                )
                               .build();
            }
            case 1 -> {//좋아요
                return ResponseTabDto.builder()
                        .postList(
                                postLikeRepo.findPostIdBy(pageRequest).getContent()
                                        .stream().filter(Objects::nonNull)
                                        .map(e->ResponsePostDto.builder()
                                                .postId(e)
                                                .postImg(null)
                                                .build()
                                        )
                                        .toList()
                        ).build();
            }
            case 2 -> {//팔로잉
//                responseTabDto.setPostIdList(postRepo.findPostIdByUserUuidIn(findFollowingUuids(requestTabDto.getUuid())));

            }
        }
        return responseTabDto;
    }


//-----------------  main 최신 + 카테고리 --------------------------
    @Override
    public ResponseTabDto postListCategory(List<Long> categoryIds) {
        return new ResponseTabDto(postIdList(categoryIds).stream().filter(Objects::nonNull)
                .map(id -> new ResponsePostDto(id,null))
                .toList());
    }

//---------------- 해시태그 검색---------------
    @Override
    public List<String> findhashTag(String hashTag) {
        return hashTagRepo.findHashTagByKeyword(Normalizer.normalize(hashTag,Normalizer.Form.NFD)).stream()
                .filter(Objects::nonNull)
                .map(e->Normalizer.normalize(e,Normalizer.Form.NFC))
                .toList();
    }
}