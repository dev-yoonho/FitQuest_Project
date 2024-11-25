package com.dualforce.fitquest.service.auth;

import com.dualforce.fitquest.model.dao.UserDao;
import com.dualforce.fitquest.model.dto.UserDto;
import com.dualforce.fitquest.util.JwtBlacklist;
import com.dualforce.fitquest.util.JwtUtil;
import com.dualforce.fitquest.util.PasswordUtil;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserDao userDao;

    public AuthServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public String login(String email, String password) {
        UserDto user = userDao.selectUserByEmail(email);
        if (user == null || !PasswordUtil.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password");
        }
        return JwtUtil.generateToken(user.getEmail());
    }

    @Override
    public void logout(String token) {
        JwtBlacklist.addToBlacklist(token);

        System.out.println("Token added to blacklist: " + token);
    }
}
