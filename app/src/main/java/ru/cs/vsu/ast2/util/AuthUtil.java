package ru.cs.vsu.ast2.util;

public final class AuthUtil {

    private AuthUtil() {
    }

    public static String toBearerToken(String token) {
        return "Bearer " + token;
    }

}
