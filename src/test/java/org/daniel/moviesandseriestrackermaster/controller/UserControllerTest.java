package org.daniel.moviesandseriestrackermaster.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.daniel.moviesandseriestrackermaster.dto.UserDTO;
import org.daniel.moviesandseriestrackermaster.models.User;
import org.daniel.moviesandseriestrackermaster.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    private UUID userId;
    private User user;
    private UserDTO userDTO;

    private final ObjectMapper objectMapper = new ObjectMapper();



    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        userDTO = new UserDTO("Daniel", "daniel@example.com");
        user = User.builder()
                .id(userId)
                .name("Daniel")
                .email("daniel@example.com")
                .build();
    }

    @Test
    void should_create_user() throws Exception{
        when(userService.createUser(any(UserDTO.class))).thenReturn(user);

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId.toString()))
                .andExpect(jsonPath("$.name").value("Daniel"))
                .andExpect(jsonPath("$.email").value("daniel@example.com"));



    }

    @Test
    void should_get_user_by_id_found() throws Exception{
        when(userService.getUserById(userId)).thenReturn(Optional.of(user));

        mockMvc.perform(get("/api/users/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId.toString()))
                .andExpect(jsonPath("$.name").value("Daniel"))
                .andExpect(jsonPath("$.email").value("daniel@example.com"));
    }

    @Test
    void should_get_user_by_id_not_found()throws Exception {
        when(userService.getUserById(userId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/users/{id}", userId)).andExpect(status().isNotFound());

    }

    @Test
    void should_find_user_by_email()throws Exception {
        when(userService.getUserByEmail("daniel@example.com")).thenReturn(Optional.of(user));
        mockMvc.perform(get("/api/users/email/{email}", userDTO.getEmail()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Daniel"))
                .andExpect(jsonPath("$.email").value("daniel@example.com"));
    }
}
