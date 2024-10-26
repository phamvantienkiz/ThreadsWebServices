package com.threads.webservices.controller;

import com.threads.webservices.service.NotificationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notifications")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class NotificationController {

    NotificationService notificationService;

}
