package com.threads.webservices.entity;

import com.threads.webservices.enums.SocialType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "social_files")
public class SocialFile implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private SocialType type;

    private String url;

    @ManyToOne()
    @JoinColumn(name = "thread_id", nullable = false)
    private Thread thread;

}
