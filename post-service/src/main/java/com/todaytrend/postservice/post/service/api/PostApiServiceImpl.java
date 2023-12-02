package com.todaytrend.postservice.post.service.api;

import com.todaytrend.postservice.post.dto.api.RecommendPostIdListDto;
import com.todaytrend.postservice.post.dto.api.RequestRecommendPostIdDto;
import com.todaytrend.postservice.post.entity.Post;
import com.todaytrend.postservice.post.repository.*;
import com.todaytrend.postservice.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostApiServiceImpl implements PostApiService {

    private final PostRepository postRepo;
    private final CategoryRepository categoryRepo;
    private final PostLikeRepository postLikeRepo;
    private final PostUserTagRepository postUserTagRepo;
    private final HashTagRepository hashTagRepo;

    @Override
    public RecommendPostIdListDto recommendPosts(RequestRecommendPostIdDto requestRecommendPostId, Pageable pageable) {
        /*   private String userUuid;
    private Integer tab;//1- 전체, 2- 팔로잉
    private String CategoryName;
*/

//findPostIdByOrderByCreateAt

        String userUuid = requestRecommendPostId.getUserUuid();
        Integer tab = requestRecommendPostId.getTab();
        List<String> categoryList = requestRecommendPostId.getCategoryNameList();//현재는 1개만 가능하다고 가정

        String categoryName = categoryList.get(0);

        switch (tab){
            case 2 -> {
                List<List<Post>> postSeletList = new ArrayList<>();
                if(categoryName.isEmpty()){//카테고리 없을 때
                    for(String followUuid : followList(userUuid)){
                        postSeletList.add(postRepo.findAllByWhereUserUuidOrderByCreateAt(userUuid, followUuid));
                    }

                }else{
//                    postRepo.
//                    categoryRepo.findByPostIdAndCategoryName(categoryName);

                }
            }
            default -> {

            }
        }

        return null;
    }

    private List<String> followList(String userUuid){
        //user서버에서 요청
        return List.of(new String[]{"userUuid1"});
    }

}
