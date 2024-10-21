package com.threads.webservices.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @ManyToOne
    @JoinColumn(name = "thread_id")
    Thread thread;

    @Column(name = "content", length = 200)
    String content;

    private String type; // like, repost, comment

    @Column(name = "create_at", nullable = false)
    LocalDateTime createAt;

    @Column(name = "is_read")
    boolean isRead;
}
