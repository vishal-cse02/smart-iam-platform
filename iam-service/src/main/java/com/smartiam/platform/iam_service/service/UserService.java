package com.smartiam.platform.iam_service.service;

import com.smartiam.platform.iam_service.dto.SignupRequest;
import com.smartiam.platform.iam_service.entity.Role;
import com.smartiam.platform.iam_service.entity.User;
import com.smartiam.platform.iam_service.repository.RoleRepository;
import com.smartiam.platform.iam_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public User registerUser(SignupRequest request){
        // Check if alrady exists
        if(userRepository.findByUsername(request.getUsername()).isPresent()){
            throw new RuntimeException("User already exists");
        }

        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new RuntimeException("Email already exist");
        }

        // Fetch role details

        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Default role does not found"));

        // Create user
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Set.of(userRole))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        return userRepository.save(user);

    }
}
