package com.todaytrend.postservice.post.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.List;

public class PostRepositoryImpl implements CustomPostRepository{

    private final JPAQueryFactory queryFactory;

    public PostRepositoryImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Long> findPostIdBy(/*String userUuid, List<String> followings, Integer tab,*/ List<String> categoryList) {
        /*BooleanExpression whereClause = Expressions.TRUE;

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

        return *//*queryFactory
                .select(post.postId)
                .from(post)
                .join(category)
                .on(post.postId.eq(category.postId))
                .where(whereClause)
                .orderBy(post.createAt.desc())
                .fetch();*//*
                queryFactory
                        .select(category.postId)
                        .from(category)
                        .rightJoin(post)
                        .on(category.postId.eq(post.postId))
                        .where(whereClause)
                        .orderBy(post.createAt.desc())
                        .fetch();*/
        /*BooleanExpression whereClause = category.categoryName.eq(categoryName.get(0));

        for (int i = 1; i < categoryName.size(); i++) {
            whereClause = whereClause.and(category.categoryName.eq(categoryName.get(i)));
        }*/

        return /*queryFactory
                .select(category.postId)
                .from(category)
                .where(whereClause)
                .fetch()*/null;
    }
}
