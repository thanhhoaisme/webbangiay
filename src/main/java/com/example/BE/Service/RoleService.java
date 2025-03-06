package com.example.BE.Service;

import com.example.BE.DTO.request.RoleRequest;
import com.example.BE.DTO.response.RoleResponse;
import com.example.BE.Mapper.RoleMapper;
import com.example.BE.Repository.PermissionRepository;
import com.example.BE.Repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class RoleService {
    RoleRepository roleRepository;
    RoleMapper roleMapper;
    PermissionRepository permissionRepository;

    public RoleResponse createRole(RoleRequest request) {
        var role = roleMapper.toRole(request);

        var permissions = permissionRepository.findAllById(request.getPermissions());
        role.setPermissions(new HashSet<>(permissions));

        role = roleRepository.save(role);
        return roleMapper.toRoleResponse(role);
    }
    public List<RoleResponse> getAllRoles(){
        return roleRepository.findAll()
                .stream().map(roleMapper::toRoleResponse)
                .toList();
    }
    public void deleteRole(String roleName){
        roleRepository.deleteById(roleName);
    }
}
