package com.example.BE.Mapper;

import com.example.BE.DTO.request.RoleRequest;
import com.example.BE.DTO.response.RoleResponse;
import com.example.BE.Entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);
    RoleResponse toRoleResponse(Role role);
}
