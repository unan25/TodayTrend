package com.todaytrend.postservice.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.todaytrend.postservice.entity.QCategory;
import com.todaytrend.postservice.entity.QPost;
import com.todaytrend.postservice.enumulator.CategoryNames;
import com.todaytrend.postservice.service.api.PostApiServiceImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.ArrayList;
import java.util.List;

import static com.todaytrend.postservice.entity.QCategory.*;
import static com.todaytrend.postservice.entity.QPost.*;

public class PostRepositoryImpl implements CustomPostRepository{

    private final JPAQueryFactory queryFactory;

    public PostRepositoryImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Long> findPostIdBy(String userUuid, List<String> followings, Integer tab, List<String> categoryList) {
        BooleanExpression whereClause = Expressions.TRUE;

        if(tab != 1){//1은 ALL 전체 조회이므로 조건 필요 없음
            if(followings != null && !followings.isEmpty()){//내가 팔로잉 하는 사람이 있을 경우
                whereClause = whereClause.and(post.userUuid.in(followings));
            }
        }

        if (categoryList != null && !categoryList.isEmpty()) {
            List<String> categoryNameList = new ArrayList<>();
            for (String categoryName : categoryList) {
                categoryNameList.add(CategoryNames.valueOf(categoryName).name());
            }
            System.out.println("categoryNameList : "+ categoryList);
            whereClause = whereClause.and(category.categoryName.in(categoryNameList));
        }

        return /*queryFactory
                .select(post.postId)
                .from(post)
                .join(category)
                .on(post.postId.eq(category.postId))
                .where(whereClause)
                .orderBy(post.createAt.desc())
                .fetch();*/
                queryFactory
                        .select(category.postId)
                        .from(category)
                        .rightJoin(post)
                        .on(category.postId.eq(post.postId))
                        .where(whereClause)
                        .orderBy(post.createAt.desc())
                        .fetch();
    }
}