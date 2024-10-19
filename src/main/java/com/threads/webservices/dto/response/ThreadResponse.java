package com.threads.webservices.dto.response;

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
    int likeCount;
    String imageUrl;
    String userId;
}
