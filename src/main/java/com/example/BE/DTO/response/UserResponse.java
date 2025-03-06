package com.example.BE.DTO.response;


//import com.example.BE.Entity.Role;
import com.example.BE.Enum.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String id;
    String username;
    String phone_number;
    String address;
    LocalDate dob;
    Set<RoleResponse> roles;
}
