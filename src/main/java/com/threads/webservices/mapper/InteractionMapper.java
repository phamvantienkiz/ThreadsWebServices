package com.threads.webservices.mapper;

import com.threads.webservices.dto.response.InteractionResponse;
import com.threads.webservices.entity.ThreadInteraction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InteractionMapper {
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "thread.id", target = "threadId")
    InteractionResponse toInteractionResponse(ThreadInteraction interaction);
}
