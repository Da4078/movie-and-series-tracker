package org.daniel.moviesandseriestrackermaster.service;

import org.daniel.moviesandseriestrackermaster.dto.SeriesDTO;
import org.daniel.moviesandseriestrackermaster.enums.GenreEnum;
import org.daniel.moviesandseriestrackermaster.models.Series;
import org.daniel.moviesandseriestrackermaster.repository.SeriesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SeriesServiceTest {

    @Mock
    private SeriesRepository seriesRepository;

    @InjectMocks
    private SeriesService seriesService;

    private UUID id;
    private Series series;
    private SeriesDTO seriesDTO;

    @BeforeEach
    void set_up(){
        id = UUID.randomUUID();
        series = Series.builder()
                .id(id)
                .title("Breaking Bad")
                .genres(List.of(GenreEnum.CRIME))
                .creator("Gilligian")
                .startYear(2008)
                .description("Chemistry teacher turns into a drug kingpin")
                .build();
        seriesDTO = new SeriesDTO();
        seriesDTO.setTitle("Breaking Bad");
        seriesDTO.setGenres(List.of(GenreEnum.CRIME));
        seriesDTO.setCreator("Gilligian");
        seriesDTO.setStartYear(2008);
        seriesDTO.setDescription("Chemistry teacher turns into a drug kingpin");
    }

    @Test
    void should_create_series(){
        when(seriesRepository.save(any(Series.class))).thenReturn(series);
        Series result = seriesService.createSeries(seriesDTO);

        assertNotNull(result);
        assertEquals("Breaking Bad", result.getTitle());

        ArgumentCaptor<Series> captor = ArgumentCaptor.forClass(Series.class);
        verify(seriesRepository).save(captor.capture());
        Series saved = captor.getValue();
        assertEquals("Breaking Bad", saved.getTitle());
        assertEquals("Gilligian", saved.getCreator());
    }

    @Test
    void should_get_series_by_id(){
        when(seriesRepository.findById(id)).thenReturn(Optional.of(series));

        Optional<Series> result = seriesService.getSeriesById(id);

        assertTrue(result.isPresent());
        assertEquals("Breaking Bad", result.get().getTitle());

        verify(seriesRepository).findById(id);
    }

    @Test
    void should_not_get_series_by_id(){
        when(seriesRepository.findById(id)).thenReturn(Optional.empty());
        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> seriesService.getSeriesById(id));
        assertTrue(exception.getMessage().contains("not found"));
    }

    @Test
    void should_get_series_with_filters() {
        when(seriesRepository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(series)));

        Page<Series> result = seriesService.getSeries(
                "break", GenreEnum.CRIME, "Gilligian", "title", "asc", 0, 10);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals("Breaking Bad", result.getContent().get(0).getTitle());

        verify(seriesRepository).findAll(any(Specification.class), any(Pageable.class));
    }

    @Test
    void should_get_series_without_filters() {

        when(seriesRepository.findAll(ArgumentMatchers.<Specification<Series>>any(), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(series)));

        Page<Series> result = seriesService.getSeries(
                null, null, null, "title", "desc", 0, 10);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());

        verify(seriesRepository).findAll(ArgumentMatchers.<Specification<Series>>any(), any(Pageable.class));
    }

    @Test
    void should_update_series(){
        when(seriesRepository.findById(id)).thenReturn(Optional.of(series));
        when(seriesRepository.save(any(Series.class))).thenReturn(series);

        seriesDTO.setTitle("The Sopranos");

        Series updated = seriesService.updateSeries(id, seriesDTO);

        assertEquals("The Sopranos", updated.getTitle());
        verify(seriesRepository).save(series);
    }

    @Test
    void should_not_update_series(){
        when(seriesRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(IllegalStateException.class, () -> seriesService.updateSeries(id, seriesDTO));

    }

    @Test
    void should_delete_series(){
        when(seriesRepository.existsById(id)).thenReturn(true);

        seriesService.deleteSeries(id);

        verify(seriesRepository).deleteById(id);
    }

    @Test
    void should_not_delete_series(){
        when(seriesRepository.existsById(id)).thenReturn(false);

        assertThrows(IllegalStateException.class, () -> seriesService.deleteSeries(id));

        verify(seriesRepository, never()).deleteById(id);
    }
}
