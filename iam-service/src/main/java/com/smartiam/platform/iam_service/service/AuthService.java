package com.smartiam.platform.iam_service.service;

import com.smartiam.platform.iam_service.dto.LoginRequest;
import com.smartiam.platform.iam_service.entity.Role;
import com.smartiam.platform.iam_service.entity.User;
import com.smartiam.platform.iam_service.repository.UserRepository;
import com.smartiam.platform.iam_service.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public String login(LoginRequest loginRequest){
        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid Credentials"));

        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
            throw new RuntimeException("Invalid Credentials");
        }
        return JwtUtil.generateToken(
                loginRequest.getUsername(),
                user.getRoles()
                        .stream()
                        .map(Role::getName)
                        .collect(Collectors.toList())
        );
    }
}
