package com.example.BE.Controller;

import com.example.BE.DTO.request.PermissionRequest;
import com.example.BE.DTO.response.APIResponse;
import com.example.BE.DTO.response.PermissionResponse;
import com.example.BE.Service.PermissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
@Slf4j
public class PermissionController {
    PermissionService permissionService;

    @PostMapping
    APIResponse<PermissionResponse> createPermission(@RequestBody @Valid PermissionRequest request){
        return APIResponse.<PermissionResponse>builder()
                .result(permissionService.createPremission(request))
                .build();
    }
    @GetMapping
    APIResponse<List<PermissionResponse>> getAllPermissions(){
        return APIResponse.<List<PermissionResponse>>builder()
                .result(permissionService.getAllPermissions())
                .build();
    }
    @DeleteMapping("/{permissionName}")
    APIResponse<Void> deletePermission(@PathVariable String permissionName){
        permissionService.deletePermission(permissionName);
        return APIResponse.<Void>builder().build();
    }
}
