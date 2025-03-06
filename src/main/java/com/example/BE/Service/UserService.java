package com.example.BE.Service;

import com.example.BE.DTO.request.CreateUserRequest;
import com.example.BE.DTO.request.UpdateUserRequest;
import com.example.BE.DTO.response.UserResponse;
import com.example.BE.Entity.User;
import com.example.BE.Enum.Role;
import com.example.BE.Exception.AppException;
import com.example.BE.Exception.ErrorCode;
import com.example.BE.Mapper.UserMapper;
//import com.example.BE.Repository.RoleRepository;
import com.example.BE.Repository.RoleRepository;
import com.example.BE.Repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;


@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
public class UserService {
    UserRepository userRepository;
    RoleRepository roleRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    public UserResponse createUser(CreateUserRequest request) {
            log.info("Creating user");
            if(userRepository.existsByUsername(request.getUsername())) {
                log.info("User with username '{}' already exists", request.getUsername());
                throw new AppException(ErrorCode.USER_EXISTED);
            }
            User user = userMapper.toUser(request);
            user.setPassword(passwordEncoder.encode(request.getPassword()));

            HashSet<String> roles = new HashSet<>();
            roles.add(Role.USER.name());
//            user.setUserRole(Role);

            return userMapper.toUserResponse(userRepository.save(user));

    }
    public UserResponse getMyInfo(){
        var  context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByUsername(name)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return userMapper.toUserResponse(user);
    }
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getAllUsers(){
        log.info("Get all users");
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }
    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponse getUserById(String id){
        log.info("Get user by id");
        return userMapper.toUserResponse( userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
    }
    public UserResponse updateUser(String id, UpdateUserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userMapper.updateUser(user, request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        var roles = roleRepository.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(roles));

        return userMapper.toUserResponse(userRepository.save(user));
    }
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(String id){
        userRepository.deleteById(id);

    }

}
