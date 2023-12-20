package com.todaytrend.postservice.comment.rabbitmq;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Producer {

    //멤버 변수로 RabbiTemplate 생성
    private  final RabbitTemplate rabbitTemplate;

    public void sendTestMessage(String message){
        // 서버 내의 1. 어떤 Queue에   2. 어떤 데이터를 보낼지 설정
        rabbitTemplate.convertAndSend("COMMENT_CREATE_QUEUE",message);
        // 이제 이걸 서비스가 호출하게 함.
    }
    public void sendCreateCommentMessage(String message) {
        rabbitTemplate.convertAndSend("COMMENT_CREATE_QUEUE", message);
    }
    // post 서버에 글 작성자 찾기
    public void sendFindUuidMessage(String message) {
        rabbitTemplate.convertAndSend("POST_FIND_UUID_QUEUE", message);
    }
}
