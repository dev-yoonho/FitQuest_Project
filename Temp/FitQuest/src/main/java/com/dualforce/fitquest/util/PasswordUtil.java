package com.dualforce.fitquest.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtil {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static String encodePassword(String rawpassword) {
        return encoder.encode(rawpassword);
    }

    public static boolean matches(String rawpassword, String encodedpassword) {
        return encoder.matches(rawpassword, encodedpassword);
    }
}
