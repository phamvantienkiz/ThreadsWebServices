package com.threads.webservices.repository;

import com.threads.webservices.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);

    @Query(value = "SELECT * FROM users WHERE " +
            "(name LIKE CONCAT('%', :keyword, '%') OR nickname LIKE CONCAT('%', :keyword, '%')) " +
            "AND username <> :username", nativeQuery = true)
    List<User> findByKeyword(String username, String keyword);
}
