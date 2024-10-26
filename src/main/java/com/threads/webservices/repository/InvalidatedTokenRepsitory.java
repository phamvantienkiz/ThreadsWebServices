package com.threads.webservices.repository;

import com.threads.webservices.entity.InvalidatedToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvalidatedTokenRepsitory extends JpaRepository<InvalidatedToken, String> {
}
