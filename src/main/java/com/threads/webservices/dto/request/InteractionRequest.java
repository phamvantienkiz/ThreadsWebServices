package com.threads.webservices.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InteractionRequest {
    boolean isLiked;
    boolean isReposted;
}
