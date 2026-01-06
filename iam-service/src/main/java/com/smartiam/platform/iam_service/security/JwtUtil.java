package com.smartiam.platform.iam_service.security;

import com.smartiam.platform.iam_service.config.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.List;

public class JwtUtil {

    private static final Key KEY =
            Keys.hmacShaKeyFor(JwtConfig.SECRET_KEY.getBytes());

    // Generate token
    public static String generateToken(String username, List<String> roles) {

        return Jwts.builder()
                .subject(username)
                .claim("roles", roles)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()
                        + JwtConfig.EXPIRATION_TIME))
                .signWith(KEY)
                .compact();
    }

    // Extract username
    public static String extractUsername(String token) {

        return getClaims(token).getSubject();
    }

    // Validate token
    public static boolean isTokenValid(String token) {
        try {
            getClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private static Claims getClaims(String token) {

        return Jwts.parser()
                .verifyWith((SecretKey) KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
