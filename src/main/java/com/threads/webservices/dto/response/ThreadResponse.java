package com.threads.webservices.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.threads.webservices.entity.Thread;
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

    public static ThreadResponse fromThread (Thread thread) {
        return ThreadResponse.builder()
               .id(thread.getId())
               .content(thread.getContent())
               .likeCount(thread.getLikeCount())
               .repostCount(thread.getRepostCount())
               .createAt(thread.getCreateAt())
               .userResponse(UserResponse.fromUser(thread.getUser()))
               .socialFileResponses(thread.getSocialFiles().stream().map(SocialFileResponse::fromSocialFiles).toList())
               .build();
    }
}
