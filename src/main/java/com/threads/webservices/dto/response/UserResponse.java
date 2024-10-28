package com.threads.webservices.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.threads.webservices.entity.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String id;
    String username;
    @JsonProperty("name")
    String name;
    LocalDate dob;
    @JsonProperty("image_url")
    String imageUrl;
    String nickname;
    String biography;
    Set<String> roles;
    @JsonProperty("threads")
    List<ThreadResponse> threadResponses;

    public static UserResponse fromUser(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .dob(user.getDob())
                .imageUrl(user.getImageUrl())
                .roles(user.getRoles())
                .nickname(user.getNickname())
                .biography(user.getBiography())
                .threadResponses(user.getThreads().isEmpty() ? null : user.getThreads().stream().map(ThreadResponse::fromThread).toList())
                .build();
    }
}
