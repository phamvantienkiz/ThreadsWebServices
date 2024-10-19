package com.threads.webservices.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.threads.webservices.entity.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentResponse {
    String id;
    String content;
    String imageUrl;

    @JsonProperty("like_count")
    int likeCount;

    @JsonProperty("repost_count")
    int repostCount;

    String userId;
    String threadId;
    String prevComment;
}
