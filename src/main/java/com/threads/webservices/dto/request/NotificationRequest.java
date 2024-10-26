package com.threads.webservices.dto.request;

import com.threads.webservices.enums.NotificationType;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotificationRequest {
    String userId;
    String threadId;
    String content;
    NotificationType type;
}
