package com.threads.webservices.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("full_name")
    String fullName;
    LocalDate dob;
    @JsonProperty("img_url")
    String imgUrl;
    Set<String> roles;
}
