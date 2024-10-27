package com.threads.webservices.service;

import com.threads.webservices.dto.request.NotificationRequest;
import com.threads.webservices.dto.response.NotificationResponse;
import com.threads.webservices.dto.websocket.NotificationWS;
import com.threads.webservices.entity.Notification;
import com.threads.webservices.entity.Thread;
import com.threads.webservices.entity.User;
import com.threads.webservices.exception.AppException;
import com.threads.webservices.exception.ErrorCode;
import com.threads.webservices.repository.NotificationRepository;
import com.threads.webservices.repository.ThreadRepository;
import com.threads.webservices.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class NotificationService {
    SimpMessagingTemplate simpMessagingTemplate;
    UserRepository userRepository;
    ThreadRepository threadRepository;
    NotificationRepository notificationRepository;

    public List<Notification> getNotifications() {
        var context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED)
        );

        List<Notification> notifications = notificationRepository.findAllByUserId(user.getId());

        return notifications;
    }

    public void sendToMessage(String userId, NotificationWS notificationWS) {
        NotificationRequest notificationRequest = NotificationRequest.builder()
                .content(notificationWS.getContent())
                .userId(userId)
                .threadId(notificationWS.getThreadId())
                .type(notificationWS.getType() == null? null : notificationWS.getType())
                .build();

        Notification notification = create(notificationRequest);

        log.info("Sending notification to {} width message: {}", userId, notificationWS);
        simpMessagingTemplate.convertAndSendToUser(
                userId,
                "/notification",
                NotificationResponse.fromNotification(notification)
        );

    }

    public Notification create(NotificationRequest notificationRequest) {

        var context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();

        User user;

        if(!notificationRequest.getUserId().isBlank()){
            user = userRepository.findById(notificationRequest.getUserId())
                    .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        } else {
            user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        }


        Thread thread = threadRepository.findById(notificationRequest.getThreadId())
                .orElseThrow(() -> new AppException(ErrorCode.THREAD_NOT_EXISTED));

        Notification notification = Notification.builder()
                .content(notificationRequest.getContent())
                .thread(thread)
                .user(user)
                .createAt(LocalDateTime.now())
                .isRead(false)
                .type(notificationRequest.getType() == null ? null : notificationRequest.getType())
                .build();

        return notificationRepository.save(notification);
    }
}
