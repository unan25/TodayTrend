package com.todaytrend.notificationservice.service;

import com.todaytrend.notificationservice.dto.RequestDto;
import com.todaytrend.notificationservice.dto.ResponseDto;
import com.todaytrend.notificationservice.entity.Notification;
import com.todaytrend.notificationservice.feignclient.UserFeignClient;
import com.todaytrend.notificationservice.feignclient.UserFeignDto;
import com.todaytrend.notificationservice.repository.NotificationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserFeignClient userFeignClient;


    public List<ResponseDto> findNotifications(String uuid){
        List<ResponseDto> data = new ArrayList<>();
        List<Notification> notifications = notificationRepository.findByReceiverAndCheckedIsFalse(uuid);
        for (Notification nc : notifications) {
            UserFeignDto sender = userFeignClient.findImageAndNickname(nc.getSender());
            data.add(ResponseDto.builder()
                    .NotificationId(nc.getNotificationId())
                    .sender(sender.getNickname())
                    .senderImage(sender.getProfileImage())
                    .content(nc.getContent())
                    .type(nc.getType())
                    .createAt(nc.getCreateAt())
                    .build());
        }
        return data;
    }
    public Long getCount(String uuid) {
        return notificationRepository.countByReceiverAndCheckedIsFalse(uuid);
    }

    @Transactional
    public String checkNotifications(List<RequestDto> requestDtoList) {
        for (RequestDto dto : requestDtoList) {
            notificationRepository.deleteById(dto.getNotificationId());
        }
        return "알림 삭제 완료";
    }
}
