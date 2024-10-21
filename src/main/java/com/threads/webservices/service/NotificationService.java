package com.threads.webservices.service;

import com.threads.webservices.dto.response.NotificationMessage;
import com.threads.webservices.entity.Notification;
import com.threads.webservices.entity.Thread;
import com.threads.webservices.entity.User;
import com.threads.webservices.exception.AppException;
import com.threads.webservices.exception.ErrorCode;
import com.threads.webservices.repository.NotificationRepository;
import com.threads.webservices.repository.ThreadRepository;
import com.threads.webservices.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final UserRepository userRepository;
    private final ThreadRepository threadRepository;

    public void sendNotification(String threadId, String content, String type){
        User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));

        Thread thread = threadRepository.findById(threadId)
                .orElseThrow(()-> new AppException(ErrorCode.THREAD_NOT_EXISTED));

        Notification notification = new Notification();
        notification.setUser(user);
        notification.setContent(content);
        notification.setThread(thread);
        notification.setType(type);

        notificationRepository.save(notification);

        // Create and send notification message via WebSocket
        NotificationMessage message = new NotificationMessage(content, user, thread, type);
        simpMessagingTemplate.convertAndSendToUser(user.getId(), "/topic/notifications", message);
    }
}
