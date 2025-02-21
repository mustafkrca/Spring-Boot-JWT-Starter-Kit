package com.example.jwtstarterkit.exceptions;

public class TokenRefreshException extends RuntimeException {
    public TokenRefreshException(String message) {
        super(message);
    }
    public TokenRefreshException(String token, String message) {
        super(String.format("Token [%s] için işlem başarısız: %s", token, message));
    }
}
