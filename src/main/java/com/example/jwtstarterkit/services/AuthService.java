package com.example.jwtstarterkit.services;

import com.example.jwtstarterkit.dtos.requests.LoginRequest;
import com.example.jwtstarterkit.dtos.requests.RegisterRequest;
import com.example.jwtstarterkit.dtos.requests.TokenRefreshRequest;
import com.example.jwtstarterkit.dtos.responses.JwtAuthenticationResponse;
import com.example.jwtstarterkit.dtos.responses.TokenRefreshResponse;
import com.example.jwtstarterkit.entities.Authority;
import com.example.jwtstarterkit.entities.RefreshToken;
import com.example.jwtstarterkit.entities.User;
import com.example.jwtstarterkit.exceptions.JwtAuthenticationException;
import com.example.jwtstarterkit.exceptions.TokenRefreshException;
import com.example.jwtstarterkit.repositories.AuthorityRepository;
import com.example.jwtstarterkit.repositories.UserRepository;
import com.example.jwtstarterkit.security.JwtTokenProvider;
import com.example.jwtstarterkit.security.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthService {

    private final JwtTokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    private static final String AUTHORITY_USER = "ROLE_USER";

    @Autowired
    public AuthService(JwtTokenProvider tokenProvider, RefreshTokenService refreshTokenService, UserRepository userRepository,
                       AuthorityRepository authorityRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.tokenProvider = tokenProvider;
        this.refreshTokenService = refreshTokenService;
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public JwtAuthenticationResponse login(LoginRequest loginRequest) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());

            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            User user = userRepository.findByUsername(loginRequest.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("Kullanıcı bulunamadı: " + loginRequest.getUsername()));

            String jwt = tokenProvider.generateToken(user);
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());
            return new JwtAuthenticationResponse(jwt, refreshToken.getToken());

        } catch (BadCredentialsException ex) {
            throw new JwtAuthenticationException("Geçersiz kullanıcı adı veya şifre", ex);
        }
    }


    public String register(RegisterRequest registerRequest) {
        if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            throw new RuntimeException("Kullanıcı adı zaten kullanılıyor.");
        }

        String hashedPassword = passwordEncoder.encode(registerRequest.getPassword());
        User newUser = new User();
        newUser.setUsername(registerRequest.getUsername());
        newUser.setPassword(hashedPassword);
        newUser.setEmail(registerRequest.getEmail());
        newUser.setZodiacSign(registerRequest.getZodiacSign());

        Set<Authority> authorities = new HashSet<>(Arrays.asList(getAuthority(AUTHORITY_USER)));
        newUser.setAuthorities(authorities);
        userRepository.save(newUser);
        return "Kullanıcı başarıyla oluşturuldu.";
    }


    public TokenRefreshResponse refreshToken(TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();
        RefreshToken token = refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .orElseThrow(() -> new TokenRefreshException("Refresh token veritabanında bulunamadı!"));
        User user = token.getUser();
        String tokenNew = tokenProvider.generateToken(user);
        return new TokenRefreshResponse(tokenNew, requestRefreshToken);
    }

    private Authority getAuthority(String authority) {
        return authorityRepository.findById(authority)
                .orElse(authorityRepository.save(new Authority(authority)));
    }

    public void logout(String token) {
        String username = tokenProvider.getUsernameFromJWT(token);
        Optional<User> user = userRepository.findByUsername(username);
        user.ifPresent(value -> refreshTokenService.deleteByUserId(value.getId()));
    }
}
