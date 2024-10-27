package com.threads.webservices.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ThreadListResponse {
    List<ThreadResponse> threads;
    int totalPages;
}
