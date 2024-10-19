package com.threads.webservices.repository;

import com.threads.webservices.entity.ThreadInteraction;
import com.threads.webservices.entity.ThreadInteractionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThreadInteractionRepository extends JpaRepository<ThreadInteraction, ThreadInteractionId> {
    List<ThreadInteraction> findByUserId(String userId);
}
