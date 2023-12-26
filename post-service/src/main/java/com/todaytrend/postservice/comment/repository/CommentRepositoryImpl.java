package com.todaytrend.postservice.comment.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.todaytrend.postservice.comment.entity.Comment;
import com.todaytrend.postservice.comment.entity.QComment;
import com.todaytrend.postservice.comment.entity.QCommentLike;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommentRepositoryImpl {

    private final JPAQueryFactory queryFactory;

    public CommentRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    public List<Comment> findParentComments(Long postId, int page, int size , String uuid) {
        QComment comment = QComment.comment;
        QComment replyComment = new QComment("replyComment");
        QCommentLike commentLike = QCommentLike.commentLike;

        List<Comment> result = queryFactory
                .selectFrom(comment)
                .leftJoin(commentLike).on(comment.commentId.eq(commentLike.commentId)) // 좋아요
                .leftJoin(replyComment).on(comment.commentId.eq(replyComment.parentId)) // 대댓글
                .groupBy(comment.commentId)
                .where(
                        comment.uuid.ne(uuid),//자기 댓글 제외
                        comment.postId.eq(postId), // 게시물 지정
                        comment.parentId.isNull() //부모 댓글만
                )
                .orderBy(commentLike.commentLikeId.count().multiply(2).add(replyComment.commentId.count().multiply(1)).desc())
                .distinct() // 중복 제거
//                .limit(size)
//                .offset((long) page * size)
                .fetch();

        return result;
    }

    public List<Comment> findReplyComments(Long commentId, int page, int size) {
        QComment comment = QComment.comment;
        QCommentLike commentLike = QCommentLike.commentLike;

        List<Comment> result = queryFactory
                .selectFrom(comment)
                .leftJoin(commentLike).on(comment.commentId.eq(commentLike.commentId)) // 좋아요
                .groupBy(comment.commentId)
                .where(
                        comment.parentId.eq(commentId) // 댓글 지정
                )
                .orderBy(commentLike.commentLikeId.count().desc())
                .distinct() // 중복 제거
//                .limit(size)
//                .offset((long) page * size)
                .fetch();

        return result;
    }
}
