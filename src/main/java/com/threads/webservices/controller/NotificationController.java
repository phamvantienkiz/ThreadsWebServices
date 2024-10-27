package com.threads.webservices.controller;

import com.threads.webservices.dto.request.ApiResponse;
import com.threads.webservices.dto.response.NotificationResponse;
import com.threads.webservices.entity.Notification;
import com.threads.webservices.service.NotificationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notifications")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class NotificationController {

    NotificationService notificationService;

    @GetMapping
    public ApiResponse<List<NotificationResponse>> getNotifications() {
        List<Notification> notifications = notificationService.getNotifications();
        return ApiResponse.<List<NotificationResponse>>builder()
                .result(notifications.stream().map(NotificationResponse::fromNotification).toList())
                .build();
    }


}
