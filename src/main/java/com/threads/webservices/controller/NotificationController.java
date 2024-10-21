package com.threads.webservices.controller;

import com.threads.webservices.dto.response.NotificationMessage;
import com.threads.webservices.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @PostMapping
    public void sendNotification(@RequestBody NotificationMessage message){
        simpMessagingTemplate
                .convertAndSendToUser(message.getUser().getId(), "/topic/notifications", message);
    }
}
