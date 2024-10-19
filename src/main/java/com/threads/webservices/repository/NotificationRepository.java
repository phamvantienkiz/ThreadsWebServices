package com.threads.webservices.repository;

import com.threads.webservices.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, String> {
    List<Notification> findByUserIdAndIsReadFalse(String userId); //Lay thong tin tb chua doc
}
