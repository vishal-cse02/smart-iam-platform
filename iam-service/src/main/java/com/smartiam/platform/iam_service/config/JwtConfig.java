package com.smartiam.platform.iam_service.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {
    public static final String SECRET_KEY = "9fA3x!L2QmT8K#z@W5H7YB0S1N6DkP$R";
    public static final long EXPIRATION_TIME = 1000 * 60 * 60;
}
