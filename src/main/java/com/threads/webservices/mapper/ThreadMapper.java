package com.threads.webservices.mapper;

import com.threads.webservices.dto.request.ThreadCreationRequest;
import com.threads.webservices.dto.request.ThreadUpdateRequest;
import com.threads.webservices.dto.request.UserUpdateRequest;
import com.threads.webservices.dto.response.ThreadResponse;
import com.threads.webservices.entity.Thread;
import com.threads.webservices.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ThreadMapper {

    @Mapping(target = "id", ignore = true) //Id duoc tao tu dong
    @Mapping(target = "user", ignore = true) // thiet lap bang SecurityContextHolder
    Thread toThread(ThreadCreationRequest request);

    @Mapping(source = "user.id", target = "userId")
    ThreadResponse toThreadResponse(Thread thread);

    void updateThread(@MappingTarget Thread thread, ThreadUpdateRequest request);
}
