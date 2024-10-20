package com.threads.webservices.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.threads.webservices.entity.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
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
    Set<String> roles;

    public static UserResponse fromUser(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .dob(user.getDob())
                .imageUrl(user.getImageUrl())
                .roles(user.getRoles())
                .build();
    }
}
