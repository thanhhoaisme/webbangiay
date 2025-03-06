package com.example.BE.Controller;

import com.example.BE.DTO.request.UpdateUserRequest;
import com.example.BE.DTO.response.APIResponse;
import com.example.BE.DTO.response.UserResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import com.example.BE.Service.UserService;
import com.example.BE.DTO.request.CreateUserRequest;


import java.util.List;


@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public class UserController {
    UserService userService;

    // Create user
    @PostMapping
    APIResponse<UserResponse> createUser(@RequestBody @Valid CreateUserRequest request){
        log.info("Create User");
        return APIResponse.<UserResponse>builder()
                .result(userService.createUser(request))
                .build();
    }
    // Get all users
    @GetMapping
    APIResponse<List<UserResponse>> getAllUsers() {
        return APIResponse.<List<UserResponse>>builder()
                .result(userService.getAllUsers())
                .build();
    }
    // Get user by id
    @GetMapping("/{id}")
    APIResponse<UserResponse> getUserById(@PathVariable String id) {
        return APIResponse.<UserResponse>builder()
                .result(userService.getUserById(id))
                .build();
    }
    // Get user info
    @GetMapping("/myInfo")
    APIResponse<UserResponse> getMyInfo() {
        return APIResponse.<UserResponse>builder()
                .result(userService.getMyInfo())
                .build();
    }
    // Update user
    @PutMapping("/{id}")
    APIResponse<UserResponse>  updateUser(@PathVariable String id, @RequestBody UpdateUserRequest request ){
        return APIResponse.<UserResponse>builder()
                .result(userService.updateUser(id,request))
                .build();
    }
    //  Delete user
    @DeleteMapping("/{id}")
    APIResponse<String> deleteUser(@PathVariable String id,@RequestBody UpdateUserRequest request){
        userService.deleteUser(id);
        return APIResponse.<String>builder()
                .result("User has been deleted")
                .build();
    }
}
