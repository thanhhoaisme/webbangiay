package com.example.BE.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;


import java.time.LocalDate;
import java.util.Set;


@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    @Column(name = "username", unique = true, columnDefinition = "VARCHAR(255) COLLATE utf8mb4_unicode_ci")
    String username;
    String phone_number;
    String password;
    String address;
    LocalDate dob;
    @ManyToMany
    Set<Role> roles;
}
