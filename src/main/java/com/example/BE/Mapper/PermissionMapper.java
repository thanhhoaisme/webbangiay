package com.example.BE.Mapper;

import com.example.BE.DTO.request.PermissionRequest;
import com.example.BE.DTO.response.PermissionResponse;
import com.example.BE.Entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);
    PermissionResponse toPermissionResponse(Permission permission);

}
