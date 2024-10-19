package com.threads.webservices.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Builder
@IdClass(CommentInteractionId.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "comment_interactions")
public class CommentInteraction {

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "comment_id", nullable = false)
    Comment comment;

    @Column(name = "like_comment", nullable = false)
    boolean likeComment;

    @Column(name = "repost", nullable = false)
    boolean repost;
}
