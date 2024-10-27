package com.threads.webservices.dto.websocket;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.threads.webservices.enums.NotificationType;
import com.threads.webservices.models.WSUserResponse;
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
    @JsonProperty("ws_user_response")
    WSUserResponse userResponse; // Người gửi
    @JsonProperty("thread_id")
    String threadId;
    NotificationType type; // like, repost, comment
}

