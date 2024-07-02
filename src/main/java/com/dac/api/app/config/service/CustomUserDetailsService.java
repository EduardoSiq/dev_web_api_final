package com.dac.api.app.config.service;

import com.dac.api.app.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final com.dac.api.app.model.User user = userRepository.findByUsername(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException("user not found")
                );

        final Set<GrantedAuthority> grantedAuthorities = user.getRole().stream()
                .map(role -> new SimpleGrantedAuthority(role.getAuthority()))
                .collect(Collectors.toSet());

        return new User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }
}
