package com.todaytrend.postservice.post.repository;

import java.util.List;

public interface CustomPostRepository {
    List<Long> findPostIdBy (/*String userUuid, List<String> followings, Integer tab, List<String> categoryList*/
            List<String> categoryName);
}
