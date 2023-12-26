package com.todaytrend.postservice.comment.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.todaytrend.postservice.comment.dto.request.RequestCommentDto;
import com.todaytrend.postservice.comment.dto.request.RequestCommentLikeDto;
import com.todaytrend.postservice.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentConsumer {

    private final ObjectMapper objectMapper;
    private final CommentService commentService;
    @RabbitListener(queues = "COMMENT_CREATE_QUEUE")
    public void createComment(String message) throws JsonProcessingException {
        // objectMapper 로  String(json) --> dto(객체)
        //objectMapper.readValue("String형식인json", 목적객체.class);
        RequestCommentDto dto = objectMapper.readValue(message, RequestCommentDto.class);
        // 서비스 호출
        commentService.createComment(dto);
    }
    @RabbitListener(queues= "COMMENT_LIKE_QUEUE")
    public void commentLike(String message) throws JsonProcessingException{
        RequestCommentLikeDto dto = objectMapper.readValue(message, RequestCommentLikeDto.class);
        commentService.commentLike(dto);
    }
}
