package com.threads.webservices.dto.websocket;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.threads.webservices.enums.NotificationType;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotificationWS {
    String content;
    @JsonProperty("user_id")
    String userId;
    @JsonProperty("thread_id")
    String threadId;
    NotificationType type; // like, repost, comment
}
