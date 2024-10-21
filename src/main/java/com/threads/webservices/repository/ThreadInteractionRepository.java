package com.threads.webservices.repository;

import com.threads.webservices.entity.ThreadInteraction;
import com.threads.webservices.entity.ThreadInteractionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ThreadInteractionRepository extends JpaRepository<ThreadInteraction, String> {
    List<ThreadInteraction> findByUserId(String userId);
    Optional<ThreadInteraction> findByUserIdAndThreadId(String userId, String threadId);
}
