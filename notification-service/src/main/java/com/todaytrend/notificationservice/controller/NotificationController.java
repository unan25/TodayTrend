package com.todaytrend.notificationservice.controller;

import com.todaytrend.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;
    @GetMapping("")
    public ResponseEntity<?> getNotification(@RequestParam(value = "uuid") String uuid) {
        return new ResponseEntity<>(notificationService.findNotifications(uuid), HttpStatus.OK);
    }
}
