package com.todaytrend.postservice.comment.rabbitmq;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentProducer {

    //멤버 변수로 RabbiTemplate 생성
    private  final RabbitTemplate rabbitTemplate;
    // 댓글 등록
    public void sendCreateCommentMessage(String message) {
        rabbitTemplate.convertAndSend("COMMENT_CREATE_QUEUE", message);
    }
    //댓글 작성자에게 알림보내기
    public void sendNcCommentLikeMessage(String message){
        rabbitTemplate.convertAndSend("NC_COMMENT_LIKE_QUEUE",message);
    }
    //댓글 태그된 사람에게 알림보내기
    public void sendNcCommentTagMessage(String message){
        rabbitTemplate.convertAndSend("NC_COMMENT_TAG_QUEUE" , message);
    }
    // post 서버에 글 작성자 찾기
    public void sendFindUuidMessage(String message) {
        rabbitTemplate.convertAndSend("POST_FIND_UUID_QUEUE", message);
    }
}
