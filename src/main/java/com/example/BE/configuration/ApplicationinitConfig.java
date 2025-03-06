package com.example.BE.configuration;

import com.example.BE.Entity.User;
import com.example.BE.Enum.Role;
import com.example.BE.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
@Slf4j
public class ApplicationinitConfig {
    PasswordEncoder passwordEncoder;


    @Bean
    ApplicationRunner applicationRunner( UserRepository userRepository) {
        return args -> {
            if(userRepository.findByUsername("admin").isEmpty()){
                var roles = new HashSet<String>();
                roles.add(Role.ADMIN.name());
                User user = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin"))
//                        .Role(Role)
                        .build();
            userRepository.save(user);
                log.warn("Admin user has been created with default password: admin, please change it after login");
            };
        };
    }
}

