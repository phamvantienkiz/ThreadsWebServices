package com.threads.webservices.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ThreadResponse {
    String id;

    String content;

    @JsonProperty("like_count")
    int likeCount;

    @JsonProperty("repost_count")
    int repostCount;

    @JsonProperty("create_at")
    LocalDateTime createAt;

    @JsonProperty("user")
    UserResponse userResponse;

    @JsonProperty("social_files")
    List<SocialFileResponse> socialFileResponses;

}
