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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    private static final AntPathRequestMatcher[] AUTH_WHITELIST = {
            new AntPathRequestMatcher("/api/users/")
    };

    private static final AntPathRequestMatcher[] ADMIN_POST_WHITELIST = {
            // admin endpoints
            new AntPathRequestMatcher("/api/events/", "POST"),
            new AntPathRequestMatcher("/api/editions/", "POST"),
    };

    private static final AntPathRequestMatcher[] ADMIN_DELETE_WHITELIST = {
            // admin endpoints
            new AntPathRequestMatcher("/api/events/**", "DELETE"),
            new AntPathRequestMatcher("/api/editions/**", "DELETE"),
    };

    private static final AntPathRequestMatcher[] ADMIN_PATCH_WHITELIST = {
            // admin endpoints
            new AntPathRequestMatcher("/api/editions/**", "PATCH")
    };

    private static final AntPathRequestMatcher[] ADMIN_PUT_WHITELIST = {
            // admin endpoints
            new AntPathRequestMatcher("/api/events/**", "PUT")
    };

    @Bean
    SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(AUTH_WHITELIST).permitAll()
                        .requestMatchers(ADMIN_POST_WHITELIST).hasRole("ADMIN")
                        .requestMatchers(ADMIN_DELETE_WHITELIST).hasRole("ADMIN")
                        .requestMatchers(ADMIN_PATCH_WHITELIST).hasRole("ADMIN")
                        .requestMatchers(ADMIN_PUT_WHITELIST).hasRole("ADMIN")
                        .anyRequest().permitAll())
                .formLogin(FormLoginConfigurer::disable)
                .httpBasic(HttpBasicConfigurer::disable)
                .addFilterBefore(securityFilter, BasicAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        final DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(final AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

}