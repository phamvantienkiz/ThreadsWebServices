package com.threads.webservices.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "threads")
public class Thread {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @Column(name = "content", nullable = false, length = 1000)
    String content;

    @Column(name = "like_count", nullable = false)
    int likeCount;

    @Column(name = "repost_count", nullable = false)
    int repostCount;

    @Column(name = "create_at", nullable = false)
    LocalDateTime createAt;

    @Column(name = "update_at")
    LocalDateTime updateAt;

    @Column(name = "image_url")
    String imageUrl;

    @OneToMany(mappedBy = "thread", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    Set<Comment> comments;

    @OneToMany(mappedBy = "thread", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    Set<ThreadInteraction> interactions;
}
