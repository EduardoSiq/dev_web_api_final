package com.dac.api.app.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final SecurityFilter securityFilter;
    private final PasswordEncoder passwordEncoder;

    private static final AntPathRequestMatcher[] AUTH_WHITELIST = {
            new AntPathRequestMatcher("/api/users/", "POST"),
    };
    private static final AntPathRequestMatcher[] ADMIN_URLS = {
            // admin endpoints
            new AntPathRequestMatcher("/api/events/", "POST"),
            new AntPathRequestMatcher("/api/editions/", "POST"),
            new AntPathRequestMatcher("/api/events/**", "DELETE"),
            new AntPathRequestMatcher("/api/editions/**", "DELETE"),
            new AntPathRequestMatcher("/api/editions/**", "PATCH"),
            new AntPathRequestMatcher("/api/events/**", "PUT"),
            new AntPathRequestMatcher("/api/users/", "GET"),
            new AntPathRequestMatcher("/api/users/**", "GET"),
            new AntPathRequestMatcher("/api/users/**", "PUT"),
            new AntPathRequestMatcher("/api/users/**", "DELETE"),

    };
    private static final AntPathRequestMatcher[] ORGANIZER_URLS = {
            // organizer endpoints
            new AntPathRequestMatcher("/api/editions/**", "PUT"),
            new AntPathRequestMatcher("/api/spaces/**", "PUT"),
            new AntPathRequestMatcher("/api/activities/**", "PUT"),
            new AntPathRequestMatcher("/api/spaces/**", "POST"),
            new AntPathRequestMatcher("/api/activities/**", "POST"),
            new AntPathRequestMatcher("/api/spaces/**", "DELETE"),
            new AntPathRequestMatcher("/api/activities/**", "DELETE"),
    };
    private static final AntPathRequestMatcher[] USER_POST_WHITELIST = {
            // organizer endpoints
            new AntPathRequestMatcher("/api/users/**", "PATCH"),
            new AntPathRequestMatcher("/api/users/self/", "DELETE"),
            new AntPathRequestMatcher("/api/activities/**", "GET"),
    };

    @Bean
    SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(AUTH_WHITELIST).permitAll()
                        //admin
                        .requestMatchers(ADMIN_URLS).hasRole("ADMIN")
                        //organizer
                        .requestMatchers(ORGANIZER_URLS).hasRole("ORGANIZER")
                        //user
                        .requestMatchers(USER_POST_WHITELIST).hasRole("USER")
                        .anyRequest().permitAll())
                .formLogin(FormLoginConfigurer::disable)
                .httpBasic(HttpBasicConfigurer::disable)
                .addFilterBefore(securityFilter, BasicAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        final DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(final AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

}