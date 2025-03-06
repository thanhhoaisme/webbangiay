package com.example.BE.Service;

import com.example.BE.DTO.request.CreateUserRequest;
import com.example.BE.DTO.response.UserResponse;
import com.example.BE.Entity.User;
import com.example.BE.Enum.Role;
import com.example.BE.Exception.AppException;
import com.example.BE.Exception.ErrorCode;
import com.example.BE.Mapper.UserMapper;
import com.example.BE.Repository.RoleRepository;
import com.example.BE.Repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository; // Thêm mock nếu cần

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private CreateUserRequest createUserRequest;
    private UserResponse userResponse;
    private User user;
    private LocalDate dob;

    @BeforeEach
    void initData() {
        dob = LocalDate.of(1999, 1, 1);

        createUserRequest = CreateUserRequest.builder()
                .username("Hoane")
                .password("12111111") // Có trong request nhưng không cần xử lý trong test
                .dob(dob)
                .address("123Saigon")
                .phone_number("0123456789")
                .build();

        userResponse = UserResponse.builder()
                .id("ac26c762-c088-41b4-9e7a-26b6c7cedbf9")
                .username("Hoane")
                .dob(dob)
                .address("123Saigon")
                .phone_number("0123456789")
                .build();

        user = User.builder()
                .id("ac26c762-c088-41b4-9e7a-26b6c7cedbf9")
                .username("Hoane")
                .dob(dob)
                .address("123Saigon")
                .phone_number("0123456789")
                .roles(new HashSet<>()) // Thêm roles để khớp với logic
                .build();
    }

    @Test
    void createUser_validRequest_success() {
        // GIVEN
        Mockito.when(userRepository.existsByUsername(anyString())).thenReturn(false);
        Mockito.when(userMapper.toUser(any(CreateUserRequest.class))).thenReturn(user);
        Mockito.when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        Mockito.when(userRepository.save(any(User.class))).thenReturn(user);
        Mockito.when(userMapper.toUserResponse(any(User.class))).thenReturn(userResponse);

        // WHEN
        UserResponse response = userService.createUser(createUserRequest);

        // THEN
        assertThat(response.getId()).isNotNull().isEqualTo("ac26c762-c088-41b4-9e7a-26b6c7cedbf9");
        assertThat(response.getUsername()).isNotNull().isEqualTo("Hoane");
    }
    @Test
    void createUser_userAlreadyExists() {
        // GIVEN
        Mockito.when(userRepository.existsByUsername(anyString())).thenReturn(true);

        // WHEN
        var exception = assertThrows(AppException.class, () -> userService.createUser(createUserRequest));

        // THEN
        assertThat(exception.getErrorCode().getCode()).isEqualTo(1002); // USER_EXISTED
    }

//    @Test
//    void createUser_invalidName_fail()throws Exception {
//        // GIVEN
//        createUserRequest.setUsername("Hoane");
//        Object objectMapper = new ObjectMapper();
//        ((ObjectMapper) objectMapper).registerModule(new JavaTimeModule());
//        String content = ((ObjectMapper) objectMapper).writeValueAsString(createUserRequest);
//
//
//        // WHEN
//        var exception = assertThrows(AppException.class, () -> userService.createUser(invalidRequest));
//
//        // THEN
//        assertThat(exception.getErrorCode().getCode()).isEqualTo(1003); // INVALID_NAME
//    }
}