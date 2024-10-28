package com.threads.webservices.repository;

import com.threads.webservices.entity.Thread;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ThreadRepository extends JpaRepository<Thread, String> {
    List<Thread> findByUserId(String userId);
    @Query(value = "SELECT * FROM threads " +
            "where user_id = :userId " +
            "AND previous_thread_id IS NOT NULL", nativeQuery = true)
    List<Thread> findByPreviousThread(@Param("userId") String userId);

    @Query(value = "SELECT * FROM threads " +
            "where previous_thread_id = :previous_thread_id", nativeQuery = true)
    List<Thread> findByPreviousThreadId(@Param("previous_thread_id") String previous_thread_id);
}
