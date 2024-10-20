package com.threads.webservices.repository;

import com.threads.webservices.entity.SocialFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SocialFileRepository extends JpaRepository<SocialFile, String> {

}
