package com.todaytrend.postservice.post.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.todaytrend.postservice.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostConsumer {

    private final ObjectMapper objectMapper;
    private final PostRepository postRepository;
    private final PostProducer postProducer;

    @RabbitListener(queues = "POST_FIND_UUID_QUEUE")
    public void findReceiver(String message) throws JsonProcessingException {
        // objectMapper 로  String(json) --> dto(객체)
        //objectMapper.readValue("String형식인json", 목적객체.class);
        MessageDto messageDto = objectMapper.readValue(message, MessageDto.class);
        // 다시 객체 > String으로 변환
        messageDto.setPostWriter(postRepository.findUserUuidByPostId(messageDto.getPostId()).get(0));
        String m = objectMapper.writeValueAsString(messageDto);
        // 알림 메세지큐에 다시 적재
        postProducer.sendNcCommentCreateMessage(m);
    }
}


