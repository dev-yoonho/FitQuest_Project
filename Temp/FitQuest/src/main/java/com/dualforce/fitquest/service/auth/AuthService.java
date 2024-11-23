package com.dualforce.fitquest.service.auth;

public interface AuthService {
    // 로그인
    String login(String email,String password);

    // 로그아웃
    void logout(String token);
}
