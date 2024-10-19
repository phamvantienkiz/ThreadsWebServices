package com.threads.webservices.repository;

import com.threads.webservices.entity.Thread;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SearchRepository extends JpaRepository<Thread, String> {
    List<Thread> findByContentContaining(String keyword); //Tim thread theo tu khoa trong noi dung
}
