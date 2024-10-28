package com.threads.webservices.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.threads.webservices.entity.ThreadInteraction;
import lombok.Builder;

@Builder
public class ThreadInteractionResponse {
    @JsonProperty("user_id")
    String userId;
    @JsonProperty("thread_id")
    String threadId;
    @JsonProperty("is_repost")
    boolean repost;
    @JsonProperty("is_liked")
    boolean liked;

    public static ThreadInteractionResponse froThreadInteraction(ThreadInteraction threadInteraction) {
        return ThreadInteractionResponse.builder()
               .userId(threadInteraction.getUser().getId())
               .threadId(threadInteraction.getThread().getId())
               .repost(threadInteraction.isRepost())
               .liked(threadInteraction.isLiked())
               .build();
    }
}
