package org.daniel.moviesandseriestrackermaster.controller;

import org.daniel.moviesandseriestrackermaster.dto.WatchStatusDTO;
import org.daniel.moviesandseriestrackermaster.enums.WatchStatusEnum;
import org.daniel.moviesandseriestrackermaster.models.WatchStatus;
import org.daniel.moviesandseriestrackermaster.service.WatchStatusService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WatchStatusControllerTest {
    @Mock
    private WatchStatusService watchStatusService;

    @InjectMocks
    private WatchStatusController watchStatusController;

    private UUID userId;
    private UUID contentId;
    private WatchStatus watchStatus;
    private WatchStatusDTO watchStatusDTO;

    @BeforeEach
    void setUp() {
        userId = UUID.fromString("578ad8cf-fcd2-4e43-8deb-926d496f1996");;

        watchStatusDTO = new WatchStatusDTO(WatchStatusEnum.WATCHING);

        watchStatus = WatchStatus.builder()
                .id(userId)
                .watchStatusEnum(WatchStatusEnum.WATCHING)
                .build();
    }

    @Test
    void should_mark_status() {
        when(watchStatusService.markStatus(userId, contentId, WatchStatusEnum.WATCHING))
                .thenReturn(watchStatus);

        ResponseEntity<WatchStatus> response =
                watchStatusController.markStatus(userId, contentId, watchStatusDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(watchStatus, response.getBody());
        verify(watchStatusService).markStatus(userId, contentId, WatchStatusEnum.WATCHING);
    }

    @Test
    void should_get_by_status() {
        List<WatchStatus> statuses = List.of(watchStatus);
        when(watchStatusService.filterByStatus(userId, WatchStatusEnum.WATCHING))
                .thenReturn(statuses);

        ResponseEntity<List<WatchStatus>> response =
                watchStatusController.getByStatus(userId, WatchStatusEnum.WATCHING);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(watchStatus, response.getBody().get(0));
        verify(watchStatusService).filterByStatus(userId, WatchStatusEnum.WATCHING);
    }
}

