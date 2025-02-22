package com.example.jwtstarterkit.security;

import com.example.jwtstarterkit.entities.RefreshToken;
import com.example.jwtstarterkit.entities.User;
import com.example.jwtstarterkit.security.RefreshTokenService;
import com.example.jwtstarterkit.services.AuthService;
import com.example.jwtstarterkit.services.UserService;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    @Lazy
    private AuthService authService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");

        User user = authService.findOrCreateUser(email, oAuth2User);

        String jwt = jwtTokenProvider.generateToken(user);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String jsonResponse = String.format("{\"timestamp\": \"%s\", \"success\": true, \"data\": {\"accessToken\": \"%s\", \"refreshToken\": \"%s\", \"tokenType\": \"Bearer\"}}",
                java.time.Instant.now(), jwt, refreshToken.getToken());

        response.getWriter().write(jsonResponse);
        response.getWriter().flush();
    }

}
