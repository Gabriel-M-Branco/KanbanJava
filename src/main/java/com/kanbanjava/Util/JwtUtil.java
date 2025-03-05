package com.kanbanjava.Util;

import com.kanbanjava.Config.JwtConfig;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
@Component
public class JwtUtil {

    private final SecretKey secretKey;

    private static final long EXPIRATION_TIME = 3 * 60 * 60 * 1000;

    public JwtUtil(JwtConfig jwtConfig) {
        this.secretKey = jwtConfig.secretKey();
        log.debug("SecretKey no JwtUtil: {}", secretKey);
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