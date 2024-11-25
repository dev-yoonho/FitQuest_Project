package com.dualforce.fitquest.controller;

import com.dualforce.fitquest.service.auth.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpHeaders;
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
    @Operation(summary = "로그인", description = "사용자의 이메일과 비밀번호를 사용하여 인증 토큰(JWT)을 생성합니다.")
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password) {
        try {
            String token = authService.login(email, password);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + token);
            return ResponseEntity.ok()
                    .headers(headers)
                    .body("Login successful. Your token: " + token);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(401).body("Unauthorized: " + e.getMessage()); // 401 Unauthorized
        }
    }

    // 로그아웃
    @PostMapping("/logout")
    @Operation(summary = "로그아웃", description = "요청 헤더에 있는 Authorization 토큰을 사용해 사용자 로그아웃을 처리합니다.")
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

