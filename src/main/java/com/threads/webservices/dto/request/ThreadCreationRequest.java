package com.threads.webservices.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ThreadCreationRequest {
    String content;
    @JsonProperty("previous_thread_id")
    String previousThreadId;
}
