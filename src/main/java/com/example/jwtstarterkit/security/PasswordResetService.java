package com.example.jwtstarterkit.security;

import com.example.jwtstarterkit.entities.PasswordResetToken;
import com.example.jwtstarterkit.entities.User;
import com.example.jwtstarterkit.repositories.PasswordResetTokenRepository;
import com.example.jwtstarterkit.repositories.UserRepository;
import com.example.jwtstarterkit.utils.MailSenderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.time.Instant;
import java.util.UUID;

@Service
public class PasswordResetService {

    private final PasswordResetTokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailSenderUtil mailSenderUtil;

    private final long tokenValidityDurationMs = 15 * 60 * 1000; // 15 dakika

    @Autowired
    public PasswordResetService(PasswordResetTokenRepository tokenRepository, UserRepository userRepository,
                                PasswordEncoder passwordEncoder, MailSenderUtil mailSenderUtil) {
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailSenderUtil = mailSenderUtil;
    }

    public PasswordResetToken createPasswordResetToken(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Bu e-posta ile kayıtlı kullanıcı bulunamadı: " + email));
        PasswordResetToken token = new PasswordResetToken();
        token.setUser(user);
        token.setToken(UUID.randomUUID().toString());
        token.setExpiryDate(Instant.now().plusMillis(tokenValidityDurationMs));
        return tokenRepository.save(token);
    }

    public void sendPasswordResetEmail(String email, PasswordResetToken token) {
        String resetLink = "http://localhost:8080/auth/reset-password?token=" + token.getToken();
        String subject = "Şifre Sıfırlama İsteği";
        String message = "Şifrenizi sıfırlamak için aşağıdaki linke tıklayınız:\n" + resetLink;

        mailSenderUtil.sendSimpleMail(email, subject, message);
    }


    public User validatePasswordResetToken(String token) {
        PasswordResetToken resetToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Geçersiz şifre sıfırlama token'ı."));
        if (resetToken.getExpiryDate().isBefore(Instant.now())) {
            tokenRepository.delete(resetToken);
            throw new RuntimeException("Şifre sıfırlama token'ının süresi dolmuş.");
        }
        return resetToken.getUser();
    }

    @Transactional
    public void updatePassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    public void deleteToken(String token) {
        tokenRepository.findByToken(token).ifPresent(tokenRepository::delete);
    }
}
