package com.todaytrend.postservice.post.service;

import com.todaytrend.postservice.post.dto.CRUD.*;
import com.todaytrend.postservice.post.dto.CRUD.RequestPostListForMain;
import com.todaytrend.postservice.post.dto.RequestCheckLikedDto;
import com.todaytrend.postservice.post.dto.ResponseCheckLikedDto;
import com.todaytrend.postservice.post.dto.ResponseCreatedPostDto;
import com.todaytrend.postservice.post.dto.ResponseDto;
import com.todaytrend.postservice.post.entity.*;
import com.todaytrend.postservice.post.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


//--------------------------- 포스트 생성 --------------------------------
    @Override
    public ResponseCreatedPostDto makePost(responseMakePostDto responseMakePostDto) {
        String userUuid = responseMakePostDto.getUUID();

//        1. post생성
        Post post = Post.builder()
                        .userUuid(userUuid)
                        .content(responseMakePostDto.getContent())
                        .build();

        Post resultPost = postRepo.save(post);//save는 저장한 객체를 그대로 반환
        Long postId = resultPost.getPostId();

//        2.해시태그 저장
        makeHashTag(responseMakePostDto.getHashTagList(),postId);

//        3. userTag저장(todo : 1 ) nickname -> userUuid)
        makePostUserTag(responseMakePostDto.getUserTagList(),postId);

//        3. category에 저장
        makeCategory(responseMakePostDto.getCategoryIdList(),postId);

        return ResponseCreatedPostDto.builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Post Insert Success")
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
                    .userUuid("")//todo : 1-1 ) 여기에 userUuid바꾼거 넣어야함!
                    .build());
        }
    }

    //Category insert
    private void makeCategory(List<Long> categoryList, Long postId){
        for(Long id : categoryList){
            categoryRepo.save(Category.builder().adminCategoryId(id).postId(postId).build());
        }
    }


    //@nickname으로 멘션시 nickname을 이용해서 userUuid값 가져오기
   /* private String findUserUuidByNickname(String nickname){
        //todo: 1-2 ) User서버로 넘어가서 uuid가져오는 로직
        return "userUuid2";
    }*/

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
                    .statusCode(HttpStatus.OK.value())
                    .message("Post Delete Success")
                    .build();
    }

//----------------------------------------------------------------------------

//----------------------------포스트 불러오기------------------------------------

    @Override
    public ResponsePostDetailDto findPost(Long postId) {
        //todo : 1. user-server에서 데이터 받기 ( profileImage, nickName )


        //2. post불러오기 (내용, 업데이트 시간, 본인 포스트 여부)
        Post post = postRepo.findById(postId).orElseThrow(()->new RuntimeException("post가 없음"));

        return ResponsePostDetailDto.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Post was found successfully")
                .postId(post.getPostId())
                .postUserUUID(post.getUserUuid())
                .profileImage("")//todo:
                .nickName("")//todo:
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .postImgs(List.of())//todo:
                .build();
    }

//---------------------------해당 포스트 카테고리 리스트 불러오기-------------------------------------------------

    @Override
    public List<selectedCategoryListDto> findPostCategoryList(Long postId) {
        List<selectedCategoryListDto> adminCategoryList= new ArrayList<>();

        for(AdminCategory category : adminCategoryRepo.findAllByAdminCategoryIdIn(categoryRepo.findAdminCategoryIdByPostId(postId))){
            adminCategoryList.add(new selectedCategoryListDto(category.getAdminCategoryId(), category.getAdminCategoryName()));
        }

        return adminCategoryList;
    }

    //----------------------------포스트 좋아요 누르기------------------------------------
    @Override
    public boolean clickLike(RequestCheckLikedDto requestCheckLikedDto) {

        String userUuid = requestCheckLikedDto.getUUID();
        Long postId = requestCheckLikedDto.getPostId();

        boolean checkClickLike = postLikeRepo.findByUserUuidAndPostId(userUuid,postId) != null ? true : false; //T-좋아요 누른 사람

        if(checkClickLike){
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
    public boolean checkLiked(RequestCheckLikedDto requestCheckLikedDto) {
        boolean checkClickLike =
                postLikeRepo.findByUserUuidAndPostId(
                        requestCheckLikedDto.getUUID(), requestCheckLikedDto.getPostId())
                        != null ? true : false;
        return checkClickLike;
    }

    @Override
    public Integer checkLikeCnt(RequestCheckLikedDto requestCheckLikedDto) {
        return postLikeRepo.countByPostId(requestCheckLikedDto.getPostId()).intValue();
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
    public ResponsePostDetailDto updatePost( Long postId, requestUpdatePostDto requestUpdatePostDto) {

            Post post = postRepo.findById(postId).orElseThrow(()->new RuntimeException("잘못된 게시물 업데이트 요청"));
            post.updatePostContent(requestUpdatePostDto.getContent());
            hashTagRepo.deleteAllByPostId(postId);
            postUserTagRepo.deleteAllByPostId(postId);
            categoryRepo.deleteAllByPostId(postId);

            makeHashTag(requestUpdatePostDto.getHashTagList(),postId);
            makePostUserTag(requestUpdatePostDto.getUserTagList(),postId);
            makeCategory(requestUpdatePostDto.getCategoryIdList(),postId);

            List<selectedCategoryListDto> categoryList = new ArrayList<>();
            for (Long id : categoryRepo.findAdminCategoryIdByPostId(postId)){
                AdminCategory adminCategory = adminCategoryRepo.findById(id).orElseThrow(()->new RuntimeException("관리자 카테고리에 해당 카테고리id가 없습니다."));
                categoryList.add(new selectedCategoryListDto(adminCategory.getAdminCategoryId(),adminCategory.getAdminCategoryName()));
            }

            return ResponsePostDetailDto.builder()
                    .statusCode(HttpStatus.OK.value())
                    .message("Post was found successfully")
                    .postId(post.getPostId())
                    .postUserUUID(post.getUserUuid())
                    .profileImage("")//todo:
                    .nickName("")//todo:
                    .content(post.getContent())
                    .createdAt(post.getCreatedAt())
                    .postImgs(List.of())//todo:
                    .build();


    }

//----------------------------메인 페이지에서 post 추천-----------------------------------------


    @Override
    public List<Long> recommendPostForMain(RequestPostListForMain requestPostListForMain) {

        String userUuid = requestPostListForMain.getUserUuid();
        Long tab = requestPostListForMain.getTab();
        List<Long> categoryList = requestPostListForMain.getCategoryList();

        if (tab == 1L) {//전체 탭 선택(본인의 게시물도 포함되서 전체를 조회)
            return Optional.ofNullable(categoryList)//유저가 고른 카테고리 리스트
                    .filter(list -> !list.isEmpty()) //카테고리 리스트가 빈값이 아닐경우
                    .map(list -> postIdList(list)) //해당 카테고리를 가진 postId 가져옴 : List<Long>
                    .orElseGet(() -> postRepo.findPostIdBy());//고른 카테고리 리스트가 없을(list.isEmpty())일 경우 전체 리스트 최신순 조회 : List<Long>
        } else if (tab == 2L) {//팔로우 탭 선택(해당 유저가 팔로우한 유저의 게시물만 보여줌)
            return Optional.ofNullable(categoryList)
                    .filter(list -> !list.isEmpty())
                    .map(list -> postRepo.findPostIdByUserUuidInAndPostIdIn(findFollowingUuids(userUuid), postIdList(list)))
                    .orElseGet(() -> postRepo.findPostIdByUserUuidIn(findFollowingUuids(userUuid)));
        }
        return List.of();
    }

    public List<Long> postIdList(List<Long> categoryList){
        Map<Long, Long> frequencyMap = categoryRepo.findPostIdByAdminCategoryIdIn(categoryList).stream().filter(Objects::nonNull)
                .collect(Collectors.groupingBy(e -> e, Collectors.counting()));

        return frequencyMap.entrySet().stream()
                .sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    //todo : user서버가서 user에 대한 following Uuid 가져오기
    public List<String> findFollowingUuids(String userUuid){
        // 무언가 로직이 있겠지...
        return List.of("user2","user3");
    }


//----------------------------------------------------------
//    ----------- // 게시물 상세 보기 하단 게시글 리스트--------------------
    @Override
    public responseDetailPostsDto detailPostsList(String userUuid, Long postId) {
        String title1 = "";
        String title2 = "";
        List<Long> postIdList1 = new ArrayList<>();
        List<Long> postIdList2 = new ArrayList<>();
        Post post = postRepo.findByPostId(postId);
        if(!userUuid.equals(postRepo.findUserUuidByPostId(postId))&&postRepo.countByUserUuid(userUuid)>0){
            title1 = "@Nickname 님의 게시물";
            title2 = "@Nickname 님의 게시물과 비슷한 게시물";
            postIdList1 = postRepo.findPostIdByUserUuid(postRepo.findUserUuidByPostId(postId));

        }else {
            title1 = "추천 게시물";
            title2 = "@Nickname님의 게시물과 비슷한 게시물";
            postIdList1 = postRepo.findPostIdBy();
        }
        postIdList2 = categoryRepo.findPostIdByAdminCategoryIdIn(categoryRepo.findAdminCategoryIdByPostId(postId));
        List<selectedCategoryListDto> categoryListDtos = new ArrayList<>();
        for (AdminCategory adminCategory : adminCategoryRepo.findAllByAdminCategoryIdIn(categoryRepo.findAdminCategoryIdByPostId(postId))) {
            categoryListDtos.add(new selectedCategoryListDto(adminCategory.getAdminCategoryId(), adminCategory.getAdminCategoryName()));
        }

        return responseDetailPostsDto.builder()
                .title1(title1)
                .title2(title2)
                .postIdList1(postIdList1)
                .postIdList2(postIdList2)
                .categoryList(categoryListDtos)
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
}