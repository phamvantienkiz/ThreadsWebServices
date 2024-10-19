package com.threads.webservices.mapper;

import com.threads.webservices.dto.request.UserCreationRequest;
import com.threads.webservices.dto.request.UserUpdateRequest;
import com.threads.webservices.dto.response.UserResponse;
import com.threads.webservices.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);

    //@Mapping(source = "firstName", target = "lastName")
    UserResponse toUserResponse(User user);
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
