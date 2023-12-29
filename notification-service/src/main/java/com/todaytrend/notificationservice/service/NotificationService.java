package com.todaytrend.notificationservice.service;

import com.todaytrend.notificationservice.dto.RequestDto;
import com.todaytrend.notificationservice.dto.ResponseDto;
import com.todaytrend.notificationservice.entity.Notification;
import com.todaytrend.notificationservice.feignclient.ImgFeignClient;
import com.todaytrend.notificationservice.feignclient.ImgFeignDto;
import com.todaytrend.notificationservice.feignclient.UserFeignClient;
import com.todaytrend.notificationservice.feignclient.UserFeignDto;
import com.todaytrend.notificationservice.repository.NotificationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserFeignClient userFeignClient;
    private final ImgFeignClient imgFeignClient;


    public List<ResponseDto> findNotifications(String uuid){
        List<ResponseDto> data = new ArrayList<>();
        List<Notification> notifications = notificationRepository.findByReceiver(uuid);
        for (Notification nc : notifications) {
            UserFeignDto sender = userFeignClient.findImageAndNickname(nc.getSender());
            String postImage = null;
            if(nc.getPostId() != null) {
                ImgFeignDto imgFeignDto = imgFeignClient.getImageByPostId(nc.getPostId());
                postImage = imgFeignDto.getImageUrlList().get(0);
            }
            data.add(ResponseDto.builder()
                    .NotificationId(nc.getNotificationId())
                    .sender(sender.getNickname())
                    .senderImage(sender.getProfileImage())
                    .content(nc.getContent())
                    .type(nc.getType())
                    .createdBefore(createdBefore(nc.getCreatedAt()))
                    .postId(nc.getPostId())
                    .postImage(postImage)
                    .build());
        }
        return data;
    }
    public Long getCount(String uuid) {
        return notificationRepository.countByReceiver(uuid);
    }

    @Transactional
    public String checkNotifications(List<RequestDto> requestDtoList) {
        for (RequestDto dto : requestDtoList) {
            notificationRepository.deleteById(dto.getNotificationId());
        }
        return "알림 전체 삭제 완료";
    }
    @Transactional
    public String checkNotification(Long notificationId) {
        notificationRepository.deleteById(notificationId);
        return "알림 삭제 완료";
    }

    // 시간 변환
    public String createdBefore(LocalDateTime createdAt) {
        LocalDateTime currentDateTime = LocalDateTime.now();

        Duration duration = Duration.between(createdAt, currentDateTime);
        long seconds = duration.getSeconds();

        if (seconds < 60) {
            return "방금 전";
        } else if (seconds < 3600) {
            long minutes = TimeUnit.SECONDS.toMinutes(seconds);
            return minutes + "분 전";
        } else if (seconds < 86400) {
            long hours = TimeUnit.SECONDS.toHours(seconds);
            return hours + "시간 전";
        } else {
            long days = TimeUnit.SECONDS.toDays(seconds);
            return days + "일 전";
        }

    }
}
