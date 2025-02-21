package com.example.jwtstarterkit.repositories;

import com.example.jwtstarterkit.entities.RefreshToken;
import com.example.jwtstarterkit.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    void deleteByUser(User user);
}
