package com.dualforce.fitquest.controller;

import com.dualforce.fitquest.service.auth.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password) {
        try {
            String token = authService.login(email, password);
            return ResponseEntity.ok(token);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(401).body(e.getMessage()); // 401 Unauthorized
        }
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String token) {
        try {
            String processedToken = token.startsWith("Bearer ") ? token.substring(7) : token;
            authService.logout(processedToken);
            return ResponseEntity.ok("Successfully logged out.");
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Failed to log out: " + e.getMessage()); // 400 Bad Request
        }
    }
}
