package com.threads.webservices.service;

import com.threads.webservices.dto.request.ThreadCreationRequest;
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
public class CommentService {
    ThreadRepository threadRepository;
    UserRepository userRepository;
    ThreadMapper threadMapper;

    String getContext(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    User getUserByToken(String username){
        return userRepository.findByUsername(username)
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));
    }

    public ThreadResponse createComment(ThreadCreationRequest request){
        User user = getUserByToken(getContext());

        Thread thread = threadMapper.toThread(request);
        thread.setUser(user);
        thread.setCreateAt(LocalDateTime.now());

        return ThreadResponse.fromThread(threadRepository.save(thread));
    }

    public List<ThreadResponse> getUserComments(){
        User user = getUserByToken(getContext());

        List<Thread> threads = threadRepository.findByUserId(user.getId());

        List<Thread> comments = null;

        for (Thread comment : threads){
            if (comment.getPreviousThread() != null){
                comments.add(comment);
            }
        }

        return comments.stream().map(ThreadResponse::fromThread).toList();
    }

    public List<ThreadResponse> getCommentByThreadId(String threadId){
//        List<Thread> threads = threadRepository.findAll();

//        List<Thread> comments = null;
//
//        for (Thread comment : threads){
//            if (comment.getPreviousThread().equals(threadId)){
//                comments.add(comment);
//            }
//        } //neu k tao duoc repo thi dung cach nay

        List<Thread> comments = threadRepository.findByPreviousThread(threadId);

        return comments.stream().map(ThreadResponse::fromThread).toList();
    }

    public List<ThreadResponse> getCommentByThreadIdAndUserId(String userId, String threadId){
        List<Thread> cmts = threadRepository.findByPreviousThread(threadId);

        List<Thread> comments = null;
        for (Thread cmt : cmts){
            if (cmt.getUser().getId().equals(userId) && cmt.getPreviousThread().equals(threadId)){
                comments.add(cmt);
            }
        }

        return comments.stream().map(ThreadResponse::fromThread).toList();
    }

    public void deleteComment(String commentId){
        User user = getUserByToken(getContext());

        Thread comment = threadRepository.findById(commentId)
                .orElseThrow(()-> new AppException(ErrorCode.THREAD_NOT_EXISTED));

        if (!comment.getUser().getId().equals(user.getId())){
            throw new AppException(ErrorCode.THREAD_NOT_ALLOWED);
        } else {
            threadRepository.deleteById(commentId);
        }
    }
}
