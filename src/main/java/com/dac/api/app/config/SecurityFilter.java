package com.dac.api.app.config;

import com.dac.api.app.model.User;
import com.dac.api.app.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain) {
        try {

            final String username = request.getHeader("Username");

            final Optional<User> user = this.userRepository.findByUsername(username);

            if (user.isPresent()) {
                final User authUser = user.get();
                if (!passwordEncoder.matches(request.getHeader("Password"), authUser.getPassword())) {
                    filterChain.doFilter(request, response);
                }
                request.setAttribute("username", username);
                final UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user,
                        null,
                        authUser.getRole());

                SecurityContextHolder.getContext().setAuthentication(auth);
            }

            filterChain.doFilter(request, response);
        } catch (final Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
