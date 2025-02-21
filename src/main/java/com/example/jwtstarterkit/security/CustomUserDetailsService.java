package com.example.jwtstarterkit.security;

import com.example.jwtstarterkit.entities.Authority;
import com.example.jwtstarterkit.entities.User;
import com.example.jwtstarterkit.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User with username: "+ username+ " not found");
        }
        String userName = user.get().getUsername();
        String password = user.get().getPassword();
        List<SimpleGrantedAuthority> grantedAuthorities = mapAuthorityToSimpleGrantedAuthority(user.get().getAuthorities());
        return new org.springframework.security.core.userdetails.User(userName, password, grantedAuthorities);
    }

    private List<SimpleGrantedAuthority> mapAuthorityToSimpleGrantedAuthority(Set<Authority> authorities) {
        return authorities.stream().map(Authority::getAuthority).map(SimpleGrantedAuthority::new).toList();
    }
}