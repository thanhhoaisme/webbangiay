package com.example.BE.Controller;

import com.example.BE.DTO.request.CreateUserRequest;
import com.example.BE.DTO.response.APIResponse;
import com.example.BE.DTO.response.UserResponse;
import com.example.BE.Service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService; // Mock UserService

    @InjectMocks
    private UserController userController; // Inject mock vào controller

    private CreateUserRequest createUserRequest;
    private UserResponse userResponse;
    private LocalDate dob;

    @BeforeEach
    void initData() {
        // Khởi tạo Mockito
        MockitoAnnotations.openMocks(this);

        // Cấu hình MockMvc thủ công
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        dob = LocalDate.of(1999, 1, 1);

        createUserRequest = CreateUserRequest.builder()
                .username("Hoane")
                .password("12111111")
                .dob(dob)
                .address("123Saigon")
                .phone_number("0123456789")
                .build();

        userResponse = UserResponse.builder()
                .id("ac26c762-c088-41b4-9e7a-26b6c7cedbf9") // Dùng ID bạn cung cấp
                .username("Hoane")
                .dob(dob)
                .address("123Saigon")
                .phone_number("0123456789")
                .build();
    }

    @Test
    void createUser_validRequest_success() throws Exception {
        // GIVEN
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String content = objectMapper.writeValueAsString(createUserRequest);

        // Mock hành vi của userService
        Mockito.when(userService.createUser(ArgumentMatchers.any(CreateUserRequest.class)))
                .thenReturn(userResponse);

        // WHEN, THEN
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(status().isOk())
                .andExpect(jsonPath("result.id").value("ac26c762-c088-41b4-9e7a-26b6c7cedbf9"))
                .andExpect(jsonPath("result.username").value("Hoane"));
    }
}