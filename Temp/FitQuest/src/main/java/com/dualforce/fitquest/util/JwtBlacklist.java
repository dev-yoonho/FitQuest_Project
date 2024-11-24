package com.dualforce.fitquest.util;

import java.util.concurrent.ConcurrentHashMap;

public class JwtBlacklist {
    private static final ConcurrentHashMap<String, Boolean> blacklist = new ConcurrentHashMap<>();

    public static void addToBlacklist(String token) {
        blacklist.put(token, true);
    }

    public static boolean isBlacklisted(String token) {
        return blacklist.containsKey(token);
    }
}
