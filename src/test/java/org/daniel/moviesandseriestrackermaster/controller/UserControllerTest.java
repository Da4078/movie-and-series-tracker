package org.daniel.moviesandseriestrackermaster.controller;

import org.daniel.moviesandseriestrackermaster.dto.UserDTO;
import org.daniel.moviesandseriestrackermaster.models.User;
import org.daniel.moviesandseriestrackermaster.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private UUID userId;
    private User user;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        userId = UUID.fromString("578ad8cf-fcd2-4e43-8deb-926d496f1996");
        userDTO = new UserDTO("Daniel", "daniel@example.com");
        user = User.builder()
                .id(userId)
                .name("Daniel")
                .email("daniel@example.com")
                .build();
    }

    @Test
    void should_create_user() {
        when(userService.createUser(userDTO)).thenReturn(user);

        ResponseEntity<User> response = userController.createUser(userDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
        verify(userService).createUser(userDTO);
    }

    @Test
    void should_get_user_by_id_found() {
        when(userService.getUserById(userId)).thenReturn(Optional.of(user));

        ResponseEntity<User> response = userController.getUserById(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
        verify(userService).getUserById(userId);
    }

    @Test
    void should_get_user_by_id_not_found() {
        when(userService.getUserById(userId)).thenReturn(Optional.empty());

        ResponseEntity<User> response = userController.getUserById(userId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(userService).getUserById(userId);
    }

    @Test
    void should_find_user_by_email() {
        when(userService.getUserByEmail("daniel@example.com")).thenReturn(Optional.of(user));

        ResponseEntity<Optional<User>> response = userController.findByEmail("daniel@example.com");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isPresent());
        assertEquals(user, response.getBody().get());
        verify(userService).getUserByEmail("daniel@example.com");
    }
}
