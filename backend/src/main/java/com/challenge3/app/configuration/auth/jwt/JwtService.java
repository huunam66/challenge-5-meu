package com.challenge3.app.configuration.auth.jwt;

import org.springframework.security.core.userdetails.UserDetails;


public interface JwtService {
    String extractToken(String token);
    Boolean validateToken(String token, UserDetails userDetails);
    String generateToken(UserDetails userDetails);
}
