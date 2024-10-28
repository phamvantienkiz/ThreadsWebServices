package com.threads.webservices.dto.response;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.threads.webservices.entity.Notification;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotificationResponse {

    String content;

    @JsonProperty("create_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    LocalDateTime createAt;

    @JsonProperty("thread")
    ThreadResponse threadResponse;

    @JsonProperty("is_read")
    boolean isRead;

    @JsonProperty("user")
    UserResponse userResponse;

    public static NotificationResponse fromNotification(Notification notification) {
        return NotificationResponse.builder()
                .content(notification.getContent())
                .isRead(notification.isRead())
                .createAt(notification.getCreateAt())
                .threadResponse(ThreadResponse.fromThread(notification.getThread()))
                .userResponse(UserResponse.fromUser(notification.getUser()))
                .build();
    }

}
