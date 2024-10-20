package com.threads.webservices.entity;

import com.threads.webservices.enums.SocialType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "social_files")
public class SocialFile {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Enumerated(EnumType.STRING)
    private SocialType type;

    private String url;

    @ManyToOne()
    @JoinColumn(name = "thread_id", nullable = false)
    private Thread thread;

}
