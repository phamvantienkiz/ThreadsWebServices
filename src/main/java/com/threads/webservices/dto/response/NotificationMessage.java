package com.threads.webservices.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.threads.webservices.entity.Thread;
import com.threads.webservices.entity.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotificationMessage {
    String content;

    @JsonProperty("user")
    User user;

    @JsonProperty("thread")
    Thread thread;
    String type; // like, repost, comment
}
