package org.daniel.moviesandseriestrackermaster.repository;

import org.daniel.moviesandseriestrackermaster.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void should_save_and_find_user_by_email() {
        User user = User.builder()
                .email("test@example.com")
                .name("Test User")
                .build();

        userRepository.save(user);

        assertTrue(userRepository.existsByEmail("test@example.com"));

        Optional<User> found = userRepository.findByEmail("test@example.com");
        assertTrue(found.isPresent());
        assertEquals("Test User", found.get().getName());
    }
}
