package com.kanbanjava.Util;

import com.kanbanjava.Config.JwtConfig;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    private final SecretKey secretKey;

    private static final long EXPIRATION_TIME = 10 * 60 * 60 * 1000;

    public JwtUtil(JwtConfig jwtConfig) {
        this.secretKey = (SecretKey) jwtConfig.secretKey();
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(secretKey)
                .compact();
    }
}