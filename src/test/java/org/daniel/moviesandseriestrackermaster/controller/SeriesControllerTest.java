package org.daniel.moviesandseriestrackermaster.controller;

import org.daniel.moviesandseriestrackermaster.dto.SeriesDTO;
import org.daniel.moviesandseriestrackermaster.enums.GenreEnum;
import org.daniel.moviesandseriestrackermaster.models.Series;
import org.daniel.moviesandseriestrackermaster.service.SeriesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SeriesControllerTest {
    @Mock
    private SeriesService seriesService;

    @InjectMocks
    private SeriesController seriesController;

    private UUID seriesId;
    private Series series;
    private SeriesDTO seriesDTO;

    @BeforeEach
    void set_up(){
        seriesId = UUID.fromString("578ad8cf-fcd2-4e43-8deb-926d496f1996");
        seriesDTO = new SeriesDTO("Breaking Bad",
                List.of(GenreEnum.CRIME),
                "Gilligian",
                2008,
                "Dreams");

        series = Series.builder()
                .id(seriesId)
                .title("Breaking Bad")
                .genres(List.of(GenreEnum.CRIME))
                .creator("Gilligian")
                .startYear(2010)
                .description("Dreams")
                .build();
    }

    @Test
    void should_create_series(){
        when(seriesService.createSeries(seriesDTO)).thenReturn(series);

        ResponseEntity<Series> response = seriesController.create(seriesDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(series, response.getBody());
        verify(seriesService).createSeries(seriesDTO);
    }

    @Test
    void should_find_series_by_id() {
        when(seriesService.getSeriesById(seriesId)).thenReturn(Optional.of(series));

        ResponseEntity<Optional<Series>> response = seriesController.findById(seriesId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isPresent());
        assertEquals(series, response.getBody().get());
        verify(seriesService).getSeriesById(seriesId);
    }

    @Test
    void should_filter_series() {
        Page<Series> page = new PageImpl<>(List.of(series));
        when(seriesService.getSeries("Breaking Bad", GenreEnum.CRIME, "Gilligian",
                "title", "asc", 0, 10)).thenReturn(page);

        ResponseEntity<Page<Series>> response = seriesController.filterSeries(
                "Breaking Bad", GenreEnum.CRIME, "Gilligian",
                "title", "asc", 0, 10);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getTotalElements());
        verify(seriesService).getSeries("Breaking Bad", GenreEnum.CRIME, "Gilligian",
                "title", "asc", 0, 10);
    }

    @Test
    void should_update_series() {
        when(seriesService.updateSeries(seriesId, seriesDTO)).thenReturn(series);

        ResponseEntity<Series> response = seriesController.update(seriesId, seriesDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(series, response.getBody());
        verify(seriesService).updateSeries(seriesId, seriesDTO);
    }

    @Test
    void should_delete_series() {
        doNothing().when(seriesService).deleteSeries(seriesId);

        ResponseEntity<Series> response = seriesController.delete(seriesId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(seriesService).deleteSeries(seriesId);
    }
}
