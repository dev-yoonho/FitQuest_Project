package com.dualforce.fitquest.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret-key}")
    private String secretKeyValue;

    @Value("${jwt.expiration-time}")
    private long expirationTimeValue;

    private static String secretKey;
    private static long expirationTime;

    @PostConstruct
    private void init() {
        secretKey = this.secretKeyValue;
        expirationTime = this.expirationTimeValue;
    }

    public static String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public static Claims validateToken(String token) {
        if (JwtBlacklist.isBlacklisted(token)) {
            throw new IllegalArgumentException("Token is blacklisted");
        }

        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }
}
