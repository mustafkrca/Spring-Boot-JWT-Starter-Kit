package com.example.jwtstarterkit.exceptions;


import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<ErrorResponse> handleException(Exception ex, HttpStatus status, String errorType) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                errorType,
                ex.getMessage()
        );

        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex) {
        return handleException(ex, HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
    }


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(ResourceNotFoundException ex) {
        return handleException(ex, HttpStatus.NOT_FOUND, "Not Found");
    }

    @ExceptionHandler(JwtAuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleJwtAuthenticationException(JwtAuthenticationException ex) {
        return handleException(ex, HttpStatus.UNAUTHORIZED, "Unauthorized");
    }

    @ExceptionHandler(TokenRefreshException.class)
    public ResponseEntity<ErrorResponse> handleTokenRefreshException(TokenRefreshException ex) {
        return handleException(ex, HttpStatus.FORBIDDEN, "Token Refresh Failed");
    }
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorResponse> handleExpiredJwtException(ExpiredJwtException ex) {
        return handleException(ex, HttpStatus.UNAUTHORIZED, "Token Expired");
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ErrorResponse> handleSignatureException(SignatureException ex) {
        return handleException(ex, HttpStatus.UNAUTHORIZED, "Invalid Token Signature");
    }

    @ExceptionHandler(InsufficientAuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientAuthenticationException(InsufficientAuthenticationException ex) {
        return handleException(ex, HttpStatus.UNAUTHORIZED, "Token yok veya Admin Yetkisi Yetersiz");
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<ErrorResponse> handleMalformedJwtException(MalformedJwtException ex) {
        return handleException(ex, HttpStatus.UNAUTHORIZED, "Geçersiz Token Formatı");
    }

    @ExceptionHandler(UnsupportedJwtException.class)
    public ResponseEntity<ErrorResponse> handleUnsupportedJwtException(UnsupportedJwtException ex) {
        return handleException(ex, HttpStatus.UNAUTHORIZED, "Desteklenmeyen Token Türü");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        return handleException(ex, HttpStatus.UNAUTHORIZED, "Token Eksik veya Geçersiz");
    }

    @ExceptionHandler(MailAuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleMailAuthenticationException(MailAuthenticationException ex) {
        return handleException(ex, HttpStatus.UNAUTHORIZED, "Mail Gönderim Servisi Kullanıcı Adı Veya Şifre Yanlış");
    }

}
