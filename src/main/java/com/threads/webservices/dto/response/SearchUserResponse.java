package com.threads.webservices.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.threads.webservices.entity.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchUserResponse {
    @JsonProperty("user_id")
    String userId;
    String nickname;
    String name;
    @JsonProperty("image_url")
    String imageUrl;

    public static SearchUserResponse fromUser(User user) {
        return SearchUserResponse.builder()
                .userId(user.getId())
                .nickname(user.getNickname())
                .name(user.getName())
                .imageUrl(user.getImageUrl())
                .build();
    }
}
