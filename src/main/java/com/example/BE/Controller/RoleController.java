package com.example.BE.Controller;


import com.example.BE.DTO.request.RoleRequest;
import com.example.BE.DTO.response.APIResponse;
import com.example.BE.DTO.response.RoleResponse;
import com.example.BE.Service.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
@Slf4j
public class RoleController {
    RoleService roleService;

    @PostMapping
    APIResponse<RoleResponse>creatRole(@RequestBody @Valid RoleRequest request){
        return APIResponse.<RoleResponse>builder()
                .result(roleService.createRole(request))
                .build();
    }
    @GetMapping
    APIResponse<List<RoleResponse>>getAllRoles(){
        return APIResponse.<List<RoleResponse>>builder()
                .result(roleService.getAllRoles())
                .build();
    }
    @DeleteMapping("/{roleName}")
    APIResponse<Void>deleteRole(@PathVariable String roleName){
        roleService.deleteRole(roleName);
        return APIResponse.<Void>builder().build();
    }
}
