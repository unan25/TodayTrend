package com.todaytrend.postservice.service;

import com.todaytrend.postservice.dto.CRUD.*;
import com.todaytrend.postservice.dto.RequestPostListForMain;
import com.todaytrend.postservice.entity.*;
import com.todaytrend.postservice.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
//@Transactional(readOnly = true)
// repo통해서만 db변경가능하게 읽기 전용으로 둠,
// 변경 감지를 위한 스냅샷 인스턴스 보관이 필요 없으므로 메모리 사용량 최적화 가능
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
    public Long makePost(String userUuid, responseMakePostDto responseMakePostDto) {

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

        return postId;
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
    public boolean removePost(String userUuid, Long postId) {

        if(postRepo.findUserUuidByPostId(postId).equals(userUuid)){//postid로 해당 post작성자의 uuid를 받아와서 본인의 게시물이 맞는지 판별
            hashTagRepo.deleteAllByPostId(postId);
            postUserTagRepo.deleteAllByPostId(postId);
            postLikeRepo.deleteAllByPostId(postId);
            categoryRepo.deleteAllByPostId(postId);
            postRepo.deleteAllByPostId(postId);
            return true;
        }
        return false;
    }

//----------------------------------------------------------------------------

//----------------------------포스트 불러오기------------------------------------

    @Override
    public responsePostDetailDto findPost(String userUuid, Long postId) {
        //todo : 1. user-server에서 데이터 받기 ( profileImage, nickName )


        //2. post불러오기 (내용, 업데이트 시간, 본인 포스트 여부)
        Post post = postRepo.findById(postId).orElseThrow(()->new RuntimeException("post가 없음"));
        boolean postOwner = post.getPostId()==postId ? true : false ; //t - 본인 게시물 f - 타인 게시물

        //3. postlike 불러오기 ( 좋아요 개수, 좋아요 누른 여부)
        Long likeCnt = postLikeRepo.countByPostId(postId);
        boolean checkClickLike = postLikeRepo.findByUserUuidAndPostId(userUuid,postId) != null ? true : false ; //T-좋아요 누른 사람

        //4. categoryList생성
        List<selectedCategoryListDto> categoryList = new ArrayList<>();
        for (Long id : categoryRepo.findAdminCategoryIdByPostId(postId)){
            AdminCategory adminCategory = adminCategoryRepo.findById(id).orElseThrow(()->new RuntimeException("관리자 카테고리에 해당 카테고리id가 없습니다."));
            categoryList.add(new selectedCategoryListDto(adminCategory.getAdminCategoryId(),adminCategory.getAdminCategoryName()));
        }

        return responsePostDetailDto.builder()
                .postId(post.getPostId())
                .profileImage("")
                .nickName("")
                .content(post.getContent())
                .updateAt(post.getUpdatedAt())
                .categoryList(categoryList)
                .likeCnt(likeCnt)
                .liked(checkClickLike)
                .postOwner(postOwner)
                .build();

    }

//----------------------------------------------------------------------------

//----------------------------포스트 좋아요 누르기------------------------------------
    @Override
    public String clickLike(String userUuid, Long postId) {

        boolean checkClickLike = postLikeRepo.findByUserUuidAndPostId(userUuid,postId) != null ? true : false; //T-좋아요 누른 사람

        if(checkClickLike){
            postLikeRepo.deleteByUserUuidAndPostId(userUuid,postId);
            postLikeRepo.countByPostId(postId);
//            false;

            return "postServiceImpl : post unlike btn click -----------";
        }else{
            postLikeRepo.save(PostLike.builder().userUuid(userUuid).postId(postId).build());
            postLikeRepo.countByPostId(postId);
//            true;
            return "postServiceImpl : post like btn click -----------";
        }
    }

//----------------------------------------------------------------------------

//----------------------------포스트 업데이트------------------------------------
    @Override
    @Transactional
    public responsePostDetailDto updatePost(String userUuid, Long postId, requestUpdatePostDto requestUpdatePostDto) {

        if(userUuid.equals(postRepo.findUserUuidByPostId(postId))){//수정하려는 user와 수정하는 게시물 작성자의 일치 여부 확인
            Post post = postRepo.findById(postId).orElseThrow(()->new RuntimeException("잘못된 게시물 업데이트 요청"));
            post.updatePostContent(requestUpdatePostDto.getContent());
            hashTagRepo.deleteAllByPostId(postId);
            postUserTagRepo.deleteAllByPostId(postId);
            categoryRepo.deleteAllByPostId(postId);

            makeHashTag(requestUpdatePostDto.getHashTagList(),postId);
            makePostUserTag(requestUpdatePostDto.getUserTagList(),postId);
            makeCategory(requestUpdatePostDto.getCategoryIdList(),postId);

            Long likeCnt = postLikeRepo.countByPostId(postId);
            boolean checkClickLike = postLikeRepo.findByUserUuidAndPostId(userUuid,postId) != null ? true : false; //T-좋아요 누른 사람

            boolean postOwner = post.getPostId()==postId ? true : false ; //t - 본인 게시물 f - 타인 게시물

            List<selectedCategoryListDto> categoryList = new ArrayList<>();
            for (Long id : categoryRepo.findAdminCategoryIdByPostId(postId)){
                AdminCategory adminCategory = adminCategoryRepo.findById(id).orElseThrow(()->new RuntimeException("관리자 카테고리에 해당 카테고리id가 없습니다."));
                categoryList.add(new selectedCategoryListDto(adminCategory.getAdminCategoryId(),adminCategory.getAdminCategoryName()));
            }

            return responsePostDetailDto.builder()
                    .postId(postId)
                    .profileImage("")//todo :
                    .nickName("")//todo:
                    .content(post.getContent())
                    .updateAt(post.getUpdatedAt())
                    .categoryList(categoryList)
                    .likeCnt(likeCnt)
                    .liked(checkClickLike)
                    .postOwner(postOwner)
                    .build();
        }

        return null;
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
}