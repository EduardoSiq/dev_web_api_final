package com.dac.api.app.config;

import com.dac.api.app.enums.UserRole;
import com.dac.api.app.model.User;
import com.dac.api.app.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collections;
import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner init(final UserRepository userRepository) {
        return args -> {
            final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            final User admin = new User("admin", encoder.encode("admin"), List.of(UserRole.ADMIN, UserRole.USER), "admin@mail.com");
            final User user = new User("user", encoder.encode("user"), Collections.singletonList(UserRole.USER), "user@mail.com");
            final User organizer = new User("organizer", encoder.encode("organizer"), List.of(UserRole.ORGANIZER, UserRole.USER), "organizer@mail.com");

            userRepository.save(admin);
            userRepository.save(user);
            userRepository.save(organizer);
        };
    }
}
