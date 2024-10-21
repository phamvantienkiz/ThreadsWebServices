package com.threads.webservices.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InteractionResponse {
    @JsonProperty("user_id")
    String userId;

    @JsonProperty("thread_id")
    String threadId;

    @JsonProperty("is_liked")
    boolean isLiked;

    @JsonProperty("is_reposted")
    boolean isReposted;
}
