package com.threads.webservices.repository;

import com.threads.webservices.entity.Thread;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThreadRepository extends JpaRepository<Thread, String> {
    List<Thread> findByUserId(String userId);

    List<Thread> findByPreviousThread(String previousThread);
}
