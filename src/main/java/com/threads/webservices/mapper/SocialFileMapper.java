package com.threads.webservices.mapper;

import com.threads.webservices.dto.response.SocialFileResponse;
import com.threads.webservices.dto.response.ThreadResponse;
import com.threads.webservices.entity.SocialFile;
import com.threads.webservices.entity.Thread;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SocialFileMapper {
    @Mapping(source = "thread.id", target = "threadId")
    SocialFileResponse toSocialFileResponse(SocialFile socialFile);
}
