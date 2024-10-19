package com.threads.webservices.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @ManyToOne
    @JoinColumn(name = "thread_id")
    Thread thread;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @ManyToOne
    @JoinColumn(name = "prev_cmt_id")
    Comment prevComment;

    @Column(name = "content", nullable = false, length = 1000)
    String content;

    @Column(name = "image_url")
    String imageUrl;

    @Column(name = "like_count", nullable = false)
    int linkCount;

    @Column(name = "repost_count", nullable = false)
    int repostCount;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    Set<CommentInteraction> interactions;
}
