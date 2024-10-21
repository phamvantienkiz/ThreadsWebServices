package com.threads.webservices.service;

import com.threads.webservices.dto.request.InteractionRequest;
import com.threads.webservices.dto.response.InteractionResponse;
import com.threads.webservices.entity.Thread;
import com.threads.webservices.entity.ThreadInteraction;
import com.threads.webservices.entity.User;
import com.threads.webservices.exception.AppException;
import com.threads.webservices.exception.ErrorCode;
import com.threads.webservices.mapper.InteractionMapper;
import com.threads.webservices.repository.ThreadInteractionRepository;
import com.threads.webservices.repository.ThreadRepository;
import com.threads.webservices.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InteractionService {
    ThreadInteractionRepository threadInteractionRepository;
    ThreadRepository threadRepository;
    UserRepository userRepository;
    InteractionMapper interactionMapper;

    String getContext(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    User getUserByToken(String username){
        return userRepository.findByUsername(username)
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));
    }

    public InteractionResponse interactionWithThread(String threadId, InteractionRequest request){
        User user = getUserByToken(getContext());

        Thread thread = threadRepository.findById(threadId)
                .orElseThrow(()-> new AppException(ErrorCode.THREAD_NOT_EXISTED));

        ThreadInteraction interaction = threadInteractionRepository.findByUserIdAndThreadId(user.getId(), threadId)
                .orElseThrow(()-> new AppException(ErrorCode.INTERACTION_NOT_EXISTED));

        interaction.setUser(user);
        interaction.setThread(thread);
        interaction.setLiked(request.isLiked());
        interaction.setRepost(request.isReposted());

        return interactionMapper.toInteractionResponse(threadInteractionRepository.save(interaction));
    }

    public void removeInteraction(String threadId) {
        User user = getUserByToken(getContext());

        ThreadInteraction interaction = threadInteractionRepository
                .findByUserIdAndThreadId(user.getId(), threadId)
                .orElseThrow(() -> new AppException(ErrorCode.THREAD_NOT_EXISTED));

        threadInteractionRepository.delete(interaction);
    }
}
