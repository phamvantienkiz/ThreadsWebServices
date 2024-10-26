package com.threads.webservices.service;

import com.threads.webservices.dto.request.NotificationRequest;
import com.threads.webservices.dto.request.ThreadCreationRequest;
import com.threads.webservices.dto.request.ThreadUpdateRequest;
import com.threads.webservices.dto.response.ThreadResponse;
import com.threads.webservices.dto.websocket.NotificationWS;
import com.threads.webservices.entity.Thread;
import com.threads.webservices.entity.ThreadInteraction;
import com.threads.webservices.entity.ThreadInteractionId;
import com.threads.webservices.entity.User;
import com.threads.webservices.enums.NotificationType;
import com.threads.webservices.exception.AppException;
import com.threads.webservices.exception.ErrorCode;
import com.threads.webservices.mapper.ThreadMapper;
import com.threads.webservices.models.WSUserResponse;
import com.threads.webservices.repository.ThreadInteractionRepository;
import com.threads.webservices.repository.ThreadRepository;
import com.threads.webservices.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ThreadService {
    ThreadRepository threadRepository;
    ThreadInteractionRepository threadInteractionRepository;
    UserRepository userRepository;
    NotificationService notificationService;
    ThreadMapper threadMapper;


    public Page<Thread> findThreads(Pageable pageable){
        return threadRepository.findAll(pageable);
    }

    public ThreadResponse createThread(ThreadCreationRequest request){
        var context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Thread thread = Thread.builder()
                .user(user)
                .content(request.getContent())
                .createAt(LocalDateTime.now())
                .build();

        if(!request.getPreviousThreadId().isEmpty()) {
            Thread previousThread = threadRepository.findById(request.getPreviousThreadId()).orElseThrow(
                    () -> new AppException(ErrorCode.THREAD_NOT_EXISTED)
            );

            thread.setPreviousThread(previousThread);

            notificationService.sendToMessage(previousThread.getUser().getId(),
                    NotificationWS.builder()
                            .content(String.format("%s đã trả lời thread của bạn !", user.getNickname()))
                            .type(NotificationType.COMMENT)
                            .threadId(previousThread.getId())
                            .userResponse(
                                    WSUserResponse.builder()
                                            .userId(previousThread.getUser().getId())
                                            .nickname(user.getNickname())
                                            .build()
                            )
                            .build()
            );
        }
        Thread threadSaved = threadRepository.save(thread);
        return ThreadResponse.fromThread(threadSaved);
    }

    public List<ThreadResponse> getThreadsByUser(){
        // dung jwt de lay thong tin user sau do tra ve list thread cua user do
        var context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        List<Thread> threads = threadRepository.findByUserId(user.getId());

        return threads.stream().map(ThreadResponse::fromThread).toList();
    }

    public ThreadResponse updateThread(String threadId, ThreadUpdateRequest request){
        var context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Thread thread = threadRepository.findById(threadId)
                        .orElseThrow(() -> new AppException(ErrorCode.THREAD_NOT_EXISTED));

        if (!thread.getUser().getId().equals(user.getId())){
            throw new AppException(ErrorCode.THREAD_NOT_ALLOWED);
        }

        thread.setContent(request.getContent());

        // Cập nhật imageUrl nếu có
        if (request.getImageUrl() != null && !request.getImageUrl().isEmpty()) {
            thread.setImageUrl(request.getImageUrl());
        } else {
            thread.setImageUrl(null);  // Xóa ảnh nếu không có
        }

        thread.setUpdateAt(LocalDateTime.now());

        return ThreadResponse.fromThread(threadRepository.save(thread));
    }

    public void deleteThread(String threadId){
        var context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));

        Thread thread = threadRepository.findById(threadId)
                .orElseThrow(()-> new AppException(ErrorCode.THREAD_NOT_EXISTED));

        if (!thread.getUser().getId().equals(user.getId())){
            throw new AppException(ErrorCode.THREAD_NOT_ALLOWED);
        } else {
            threadRepository.deleteById(threadId);
        }
    }

    public void repost(String threadId, String userId) {
        ThreadInteractionId threadInteractionId = new ThreadInteractionId(userId, threadId);
        Optional<ThreadInteraction> threadInteractionExitedOptional = threadInteractionRepository.findById(threadInteractionId);

        var context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();
        String content = "";

        // Nếu tương tác không tồn tại tạo mới lại trạng thái
        if(threadInteractionExitedOptional.isEmpty()) {

            User user = userRepository.findByUsername(username)
                    .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));

            Thread thread = threadRepository.findById(threadId)
                    .orElseThrow(()-> new AppException(ErrorCode.THREAD_NOT_EXISTED));

            ThreadInteraction threadInteraction = ThreadInteraction.builder()
                    .user(user)
                    .thread(thread)
                    .liked(false)
                    .repost(true) // Người dùng đang đăng nhập đã đăng lại bài viết
                    .build();

            // Tạo tương tác mới
            threadInteractionRepository.save(threadInteraction);

            // Gửi thông báo bằng websocket khi người dùng đang online
            content = String.format("Người dùng %s đã đăng lại bài viết %s của bạn", username, threadInteraction.getThread().getId());
            notificationService.sendToMessage(
                    thread.getUser().getId(), // Người nhận
                    NotificationWS.builder()
                            .content(content)
                            .type(NotificationType.REPOST)
                            .userResponse(
                                    com.threads.webservices.models.WSUserResponse.builder()
                                            .build()
                            )
                            .build()
            );
        } else {
            // Nếu tương tác có tồn tại thì update lại trạng thái
            ThreadInteraction threadInteractionExited = threadInteractionExitedOptional.get();
            threadInteractionExited.setRepost(!threadInteractionExited.isRepost());

            // Gửi thông báo bằng websocket khi người dùng đang online
            content = String.format("Người dùng %s đã đăng lại bài viết %s của bạn", username, threadInteractionExited.getThread().getId());
            notificationService.sendToMessage(
                    threadInteractionExited.getUser().getId(), // Người nhận
                    NotificationWS.builder()
                            .content(content)
                            .type(NotificationType.REPOST)
                            .build()
            );

            // Cập nhập tương tác người dùng
            threadInteractionRepository.save(threadInteractionExited);
        }

        // Lưu thông báo nếu người dùn chưa online
        notificationService.create(
                NotificationRequest.builder()
                        .threadId(threadId)
                        .userId(userId)
                        .content(content)
                        .build()
        );
    }


}
