package com.threads.webservices.repository;

import com.threads.webservices.entity.Thread;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ThreadRepository extends JpaRepository<Thread, String> {
    List<Thread> findByUserId(String userId);

}
