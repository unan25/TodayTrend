package com.todaytrend.notificationservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.todaytrend.notificationservice.dto.CommentCreateDto;
import com.todaytrend.notificationservice.dto.CommentLikeDto;
import com.todaytrend.notificationservice.dto.CommentTagDto;
import com.todaytrend.notificationservice.dto.FollowDto;
import com.todaytrend.notificationservice.feignclient.UserFeignClient;
import com.todaytrend.notificationservice.feignclient.UserFeignDto;
import com.todaytrend.notificationservice.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Consumer {
    private final ObjectMapper objectMapper;
    private final NotificationService notificationService;
    private final NotificationRepository notificationRepository;
    private final UserFeignClient userFeignClient;

    //    POST_LIKE,
    //    POST_TAG,
    //    COMMENT_LIKE,
    //    COMMENT_CREATE,
    //    COMMENT_TAG,
    //    FOLLOW;

    @RabbitListener(queues = "NC_POST_LIKE_QUEUE")
    public void postLike(String message) throws JsonProcessingException{

    }
    @RabbitListener(queues = "NC_POST_TAG_QUEUE")
    public void postTag(String message)throws JsonProcessingException {

    }
    @RabbitListener(queues = "NC_COMMENT_LIKE_QUEUE")
    public void commentLike(String message) throws JsonProcessingException{
        CommentLikeDto commentLikeDto = objectMapper.readValue(message, CommentLikeDto.class);
        notificationRepository.save(commentLikeDto.toEntity());
    }
    @RabbitListener(queues = "NC_COMMENT_CREATE_QUEUE")
    public void commentCreate(String message) throws JsonProcessingException {
        // string 을 객체로 변환해서 db에 저장
        CommentCreateDto commentCreateDto = objectMapper.readValue(message, CommentCreateDto.class);
        notificationRepository.save(commentCreateDto.toEntity(commentCreateDto.getPostWriter()));
        if(commentCreateDto.getCommentWriter() != null){
            notificationRepository.save(commentCreateDto.toEntity(commentCreateDto.getCommentWriter()));}
    }
    @RabbitListener(queues = "NC_COMMENT_TAG_QUEUE")
    public void commentTag(String message) throws JsonProcessingException{
        CommentTagDto commentTagDto = objectMapper.readValue(message, CommentTagDto.class);
        String uuid = userFeignClient.findUuid(commentTagDto.getReceiver());// nickname을 uuid로 변환해서 저장
        notificationRepository.save(commentTagDto.toEntity(uuid));
    }
    @RabbitListener(queues = "NC_FOLLOW_QUEUE")
    public void follow(String message)throws JsonProcessingException {
        FollowDto followDto = objectMapper.readValue(message, FollowDto.class);
        notificationRepository.save(followDto.toEntity());
    }
}
