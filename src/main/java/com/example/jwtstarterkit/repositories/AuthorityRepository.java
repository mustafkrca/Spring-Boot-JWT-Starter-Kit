package com.example.jwtstarterkit.repositories;

import com.example.jwtstarterkit.entities.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, String> {
}