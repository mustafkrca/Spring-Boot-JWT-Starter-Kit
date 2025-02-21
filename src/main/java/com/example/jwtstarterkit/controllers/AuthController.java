package com.example.jwtstarterkit.controllers;

import com.example.jwtstarterkit.dtos.requests.LoginRequest;
import com.example.jwtstarterkit.dtos.requests.RegisterRequest;
import com.example.jwtstarterkit.dtos.requests.TokenRefreshRequest;
import com.example.jwtstarterkit.dtos.responses.JwtAuthenticationResponse;
import com.example.jwtstarterkit.dtos.responses.SuccessResponse;
import com.example.jwtstarterkit.dtos.responses.TokenRefreshResponse;
import com.example.jwtstarterkit.exceptions.JwtAuthenticationException;
import com.example.jwtstarterkit.services.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<SuccessResponse<JwtAuthenticationResponse>> authenticateUser(@RequestBody LoginRequest loginRequest) {
        JwtAuthenticationResponse data = authService.login(loginRequest);
        SuccessResponse response = new SuccessResponse(data);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<SuccessResponse<String>> registerUser(@RequestBody RegisterRequest registerRequest) {
        String data = authService.register(registerRequest);
        SuccessResponse response = new SuccessResponse(data);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<SuccessResponse<TokenRefreshResponse>> refreshToken(@RequestBody TokenRefreshRequest request) {
        TokenRefreshResponse data = authService.refreshToken(request);
        SuccessResponse response = new SuccessResponse(data);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<SuccessResponse<String>> logoutUser(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new JwtAuthenticationException("Token Bulunamadı");
        }

        String token = authHeader.substring(7);
        authService.logout(token);
        return ResponseEntity.ok(new SuccessResponse<>("Başarıyla çıkış yapıldı"));
    }
}
