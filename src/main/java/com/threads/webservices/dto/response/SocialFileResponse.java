package com.threads.webservices.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.threads.webservices.enums.SocialType;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SocialFileResponse {

    private String id;

    @JsonProperty("thread_id")
    private String threadId;

    private String url;

    private SocialType type;
}
