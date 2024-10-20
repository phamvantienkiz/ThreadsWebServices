package com.threads.webservices.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Builder
@IdClass(ThreadInteractionId.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "thread_interactions")
public class ThreadInteraction {

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "thread_id", nullable = false)
    Thread thread;

    @Column(name = "repost", nullable = false)
    boolean repost;

    @Column(name = "liked", nullable = false)
    boolean liked;
}
