package com.example.BE.Service;

import com.example.BE.DTO.request.PermissionRequest;
import com.example.BE.DTO.response.PermissionResponse;
import com.example.BE.Entity.Permission;
import com.example.BE.Mapper.PermissionMapper;
import com.example.BE.Repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class PermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    public PermissionResponse createPremission(PermissionRequest request) {
        Permission permission = permissionMapper.toPermission(request);
        permission = permissionRepository.save(permission);

        return permissionMapper.toPermissionResponse(permission);
    }
    public List<PermissionResponse> getAllPermissions(){
        var permissions = permissionRepository.findAll();
        return permissions.stream().map(permissionMapper::toPermissionResponse).toList();
    }
    public void deletePermission(String permissionName){
        permissionRepository.deleteById(permissionName);
    }
}
