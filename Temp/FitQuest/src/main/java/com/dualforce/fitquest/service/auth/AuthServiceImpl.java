package com.dualforce.fitquest.service.auth;

import com.dualforce.fitquest.model.dao.UserDao;
import com.dualforce.fitquest.model.dto.UserDto;

public class AuthServiceImpl implements AuthService {
    private final UserDao userDao;

    public AuthServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public String login(String email, String password) {
        UserDto user = userDao.selectUserByEmail(email);
        if (user == null || !password.equals(user.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password");
        }
        // 토큰은 어떻게 생성?
        return "";
    }

    @Override
    public void logout(String token) {
        // 토큰 무효화 로직은 어떻게?
        System.out.println("Logout token: " + token);
    }
}
