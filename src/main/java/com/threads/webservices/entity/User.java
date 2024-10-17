package com.threads.webservices.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String username;
    String password;
    String fullName;
    LocalDate dob;
    String imgUrl;
    Set<String> roles; //Set giong nhu list nhung set se dam bao k co gia tri trung lap trong list

    //Chu y vao lifecycle de clean lai khi update cac thuoc tinh co su dung mapstruct (bai 9 8:15)
}
