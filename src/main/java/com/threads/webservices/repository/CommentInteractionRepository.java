package com.threads.webservices.repository;

import com.threads.webservices.entity.CommentInteraction;
import com.threads.webservices.entity.CommentInteractionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentInteractionRepository extends JpaRepository<CommentInteraction, CommentInteractionId> {
    List<CommentInteraction> findByUserId(String userId);
}
