package com.example.BE.Mapper;

import com.example.BE.DTO.request.CreateUserRequest;
import com.example.BE.DTO.request.UpdateUserRequest;
import com.example.BE.DTO.response.UserResponse;
import com.example.BE.Entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(CreateUserRequest request);
    UserResponse toUserResponse(User user);
    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UpdateUserRequest request);

}
