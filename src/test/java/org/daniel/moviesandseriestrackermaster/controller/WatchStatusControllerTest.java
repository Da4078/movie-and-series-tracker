package org.daniel.moviesandseriestrackermaster.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.daniel.moviesandseriestrackermaster.dto.WatchStatusDTO;
import org.daniel.moviesandseriestrackermaster.enums.WatchStatusEnum;
import org.daniel.moviesandseriestrackermaster.models.Movie;
import org.daniel.moviesandseriestrackermaster.models.User;
import org.daniel.moviesandseriestrackermaster.models.WatchStatus;
import org.daniel.moviesandseriestrackermaster.service.WatchStatusService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WatchStatusController.class)
public class WatchStatusControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private WatchStatusService watchStatusService;

    @Autowired
    private ObjectMapper objectMapper;

    private UUID userId;
    private UUID contentId;
    private WatchStatus watchStatus;
    private WatchStatusDTO watchStatusDTO;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        contentId = UUID.randomUUID();

        User user = User.builder().id(userId).build();
        Movie movie = Movie.builder().id(contentId).build();

        watchStatusDTO = new WatchStatusDTO(WatchStatusEnum.WATCHED);

        watchStatus = WatchStatus.builder()
                .id(UUID.randomUUID())
                .user(user)
                .movie(movie)
                .watchStatusEnum(WatchStatusEnum.WATCHED)
                .build();
    }

    @Test
    void should_mark_status() throws Exception {
        when(watchStatusService.markStatus(any(), any(), any()))
                .thenReturn(watchStatus);

        mockMvc.perform(put("/api/watch-status/{userId}/{contentId}", userId, contentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(watchStatusDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.watchStatusEnum").value("WATCHED"));
    }

    @Test
    void should_get_status_by_user_and_enum() throws Exception {
        when(watchStatusService.filterByStatus(userId, WatchStatusEnum.WATCHED))
                .thenReturn(List.of(watchStatus));

        mockMvc.perform(get("/api/watch-status/{userId}/{status}", userId, "WATCHED"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].watchStatusEnum").value("WATCHED"));
    }
}