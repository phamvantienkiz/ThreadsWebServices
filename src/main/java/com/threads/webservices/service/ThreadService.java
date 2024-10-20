package com.threads.webservices.service;

import com.threads.webservices.dto.request.ThreadCreationRequest;
import com.threads.webservices.dto.request.ThreadUpdateRequest;
import com.threads.webservices.dto.response.ThreadResponse;
import com.threads.webservices.entity.Thread;
import com.threads.webservices.entity.User;
import com.threads.webservices.exception.AppException;
import com.threads.webservices.exception.ErrorCode;
import com.threads.webservices.mapper.ThreadMapper;
import com.threads.webservices.repository.ThreadRepository;
import com.threads.webservices.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ThreadService {
    ThreadRepository threadRepository;
    UserRepository userRepository;
    ThreadMapper threadMapper;

    public ThreadResponse createThread(ThreadCreationRequest request){
        var context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Thread thread = threadMapper.toThread(request);
        thread.setUser(user);
        thread.setCreateAt(LocalDateTime.now());

        return ThreadResponse.fromThread(threadRepository.save(thread));
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

//    public ThreadResponse updateThread(String threadId, ThreadUpdateRequest request){
//        var context = SecurityContextHolder.getContext();
//        String username = context.getAuthentication().getName();
//
//        User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
//
//        Thread thread = threadRepository.findById(threadId)
//                        .orElseThrow(() -> new AppException(ErrorCode.THREAD_NOT_EXISTED));
//
//        if (!thread.getUser().getId().equals(user.getId())){
//            throw new AppException(ErrorCode.THREAD_NOT_ALLOWED);
//        }
//
//        thread.setContent(request.getContent());
//
//        // Cập nhật imageUrl nếu có
//        if (request.getImageUrl() != null && !request.getImageUrl().isEmpty()) {
//            thread.setImageUrl(request.getImageUrl());
//        } else {
//            thread.setImageUrl(null);  // Xóa ảnh nếu không có
//        }
//
//        thread.setUpdateAt(LocalDateTime.now());
//
//        return ThreadResponse.fromThread(threadRepository.save(thread));
//    }

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
}
