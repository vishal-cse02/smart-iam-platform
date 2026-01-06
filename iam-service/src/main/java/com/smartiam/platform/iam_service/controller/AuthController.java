package com.smartiam.platform.iam_service.controller;

import com.smartiam.platform.iam_service.dto.SignupRequest;
import com.smartiam.platform.iam_service.entity.User;
import com.smartiam.platform.iam_service.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@Valid @RequestBody SignupRequest signupRequest){
        User user = userService.registerUser(signupRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("User registered successfully with id: " + user.getId());
    }
}
