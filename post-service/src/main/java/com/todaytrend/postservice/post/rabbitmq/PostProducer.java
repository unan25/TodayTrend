package com.todaytrend.postservice.post.rabbitmq;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostProducer {

    //멤버 변수로 RabbiTemplate 생성
    private  final RabbitTemplate rabbitTemplate;


    // 댓글 작성자 , 글 작성자 알림 서버에 보내기
    public void sendNcCommentCreateMessage(String message) {
        rabbitTemplate.convertAndSend("NC_COMMENT_CREATE_QUEUE", message);
    }
    public void sendNcPostLikeMessage(String message){
        rabbitTemplate.convertAndSend("NC_POST_LIKE_QUEUE", message);
    }
    public void sendNcPostTagMessage(String message) {
        rabbitTemplate.convertAndSend("NC_POST_TAG_QUEUE", message);
    }
}
