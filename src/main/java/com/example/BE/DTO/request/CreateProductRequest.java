package com.example.BE.DTO.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@NotBlank
public class CreateProductRequest {
    @NotBlank(message = "Name is required")
    private String name;
}
