package com.todaytrend.notificationservice.controller;

import com.todaytrend.notificationservice.dto.RequestDto;
import com.todaytrend.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;
    @GetMapping("")
    public ResponseEntity<?> getNotification(@RequestParam(value = "uuid") String uuid) {
        return new ResponseEntity<>(notificationService.findNotifications(uuid), HttpStatus.OK);
    }
    @GetMapping("cnt")
    public ResponseEntity<?> getCount(@RequestParam(value = "uuid") String uuid) {
        return new ResponseEntity<>(notificationService.getCount(uuid) , HttpStatus.OK);
    }
    @PostMapping("")
    public ResponseEntity<?> checkNotifications(@RequestBody List<RequestDto> notification){
        return new ResponseEntity<>(notificationService.checkNotifications(notification),HttpStatus.OK);
    }
    @DeleteMapping("")
    public ResponseEntity<?> checkNotification(Long notificationId) {
        return new ResponseEntity<>(notificationService.checkNotification(notificationId), HttpStatus.OK);
    }

}
