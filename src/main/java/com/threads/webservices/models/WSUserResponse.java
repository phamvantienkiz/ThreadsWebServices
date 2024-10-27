package com.threads.webservices.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WSUserResponse {
    @JsonProperty("user_id")
    String userId;
    String nickname;
}
