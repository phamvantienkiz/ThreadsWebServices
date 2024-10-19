package com.threads.webservices.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

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
    String imageUrl;
    String userId;
}
