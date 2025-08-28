package org.daniel.moviesandseriestrackermaster.service;

import org.daniel.moviesandseriestrackermaster.dto.UserDTO;
import org.daniel.moviesandseriestrackermaster.models.Series;
import org.daniel.moviesandseriestrackermaster.models.User;
import org.daniel.moviesandseriestrackermaster.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private UUID id;
    private User user;
    private UserDTO userDTO;

    @BeforeEach
    void set_up(){
        id = UUID.fromString("578ad8cf-fcd2-4e43-8deb-926d496f1996");
        user = User.builder().name("Daniel").email("daniel@example").build();
        userDTO = new UserDTO();
        userDTO.setName("Daniel");
        userDTO.setEmail("daniel@example.com");
    }

    @Test
    void should_create_user(){
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userService.createUser(userDTO);
        assertNotNull(result);
        assertEquals("Daniel", result.getName());

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());
        User saved = captor.getValue();
        assertEquals("Daniel", saved.getName());
        assertEquals("daniel@example.com", saved.getEmail());
    }

    @Test
    void should_get_user_by_id(){
        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        Optional<User> result = userService.getUserById(id);

        assertTrue(result.isPresent());
        assertEquals("Daniel", result.get().getName());

        verify(userRepository).findById(id);
    }

    @Test
    void should_not_get_user_by_id(){
        when(userRepository.findById(id)).thenReturn(Optional.empty());
        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> userService.getUserById(id));
        assertTrue(exception.getMessage().contains("not found"));
    }

    @Test
    void should_get_user_by_email(){
        when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(Optional.of(user));

        Optional<User> result = userService.getUserByEmail(userDTO.getEmail());

        assertTrue(result.isPresent());
        assertEquals("Daniel", result.get().getName());

        verify(userRepository).findByEmail(userDTO.getEmail());
    }

    @Test
    void should_not_get_user_by_email(){
        when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(Optional.empty());
        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> userService.getUserByEmail(userDTO.getEmail()));
        assertTrue(exception.getMessage().contains("not found"));
    }
}
