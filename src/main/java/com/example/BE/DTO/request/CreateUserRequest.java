package com.example.BE.DTO.request;


import com.example.BE.Validator.DobConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;


import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateUserRequest {
    @NotBlank(message = "NOT_NULL")
    @Size(min = 5, message = "USERNAME_INVALID")
    String username;
    @Size(min = 7, message = "PASSWORD_INVALID")
    String password;
    String phone_number;
    String address;

    @DobConstraint(min = 7,message = "INVALID_DOB")
    LocalDate dob;

}
