package com.threads.webservices.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(name = "username", unique = true)
    String username;

    @Column(name = "password")
    String password;

    @Column(name = "name")
    String name;

    @Column(name = "dob")
    LocalDate dob;

    @Column(name = "image_url")  //Url anh dai dien cua user, se dung voi hinh anh duoc upload len cloud storage
    String imageUrl;

    @Column(name = "biography")  //Mo ta cua user, se dung voi noi dung ghi chu cua user
    String biography;

    @Column(name = "nickname")  //Ten nickname cua user, se dung voi ten dang nhap cua user
    String nickname;

    Set<String> roles; //Set giong nhu list nhung set se dam bao k co gia tri trung lap trong list
    //Chu y vao lifecycle de clean lai khi update cac thuoc tinh co su dung mapstruct (bai 9 8:15)

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    Set<Thread> threads;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    Set<Notification> notifications;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    Set<ThreadInteraction> threadInteractions;



}
