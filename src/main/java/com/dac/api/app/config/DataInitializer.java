package com.dac.api.app.config;

import com.dac.api.app.enums.UserRole;
import com.dac.api.app.model.User;
import com.dac.api.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner init(final UserRepository userRepository) {
        return args -> {

            final User admin = new User("admin", passwordEncoder.encode("admin"), List.of(UserRole.ADMIN, UserRole.USER), "admin@mail.com");
            final User user = new User("user", passwordEncoder.encode("user"), Collections.singletonList(UserRole.USER), "user@mail.com");
            final User organizer = new User("organizer", passwordEncoder.encode("organizer"), List.of(UserRole.ORGANIZER, UserRole.USER), "organizer@mail.com");

            userRepository.save(admin);
            userRepository.save(user);
            userRepository.save(organizer);
        };
    }
}
