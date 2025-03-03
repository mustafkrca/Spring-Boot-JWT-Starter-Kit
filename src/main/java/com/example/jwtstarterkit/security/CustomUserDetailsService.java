package com.example.jwtstarterkit.security;

import com.example.jwtstarterkit.entities.Authority;
import com.ommy.astro.security.CustomUserDetails;
import com.example.jwtstarterkit.entities.User;
import com.example.jwtstarterkit.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username: " + username + " not found"));
        return new CustomUserDetails(user);
    }

    private List<SimpleGrantedAuthority> mapAuthorityToSimpleGrantedAuthority(Set<Authority> authorities) {
        return authorities.stream().map(Authority::getAuthority).map(SimpleGrantedAuthority::new).toList();
    }
}