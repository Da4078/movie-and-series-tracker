package org.daniel.moviesandseriestrackermaster.repository;

import org.daniel.moviesandseriestrackermaster.enums.GenreEnum;
import org.daniel.moviesandseriestrackermaster.models.Series;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class SeriesRepositoryTest {

    @Autowired
    private SeriesRepository seriesRepository;

    @Test
    void should_save_and_find_series_by_id(){
        Series series = Series.builder()
                .title("Breaking Bad")
                .genres(List.of(GenreEnum.CRIME))
                .creator("Gilligian")
                .startYear(2008)
                .description("Chemistry teacher turns into a drug kingpin")
                .build();

        Series saved = seriesRepository.save(series);

        Optional<Series> found = seriesRepository.findById(saved.getId());

        assertTrue(found.isPresent());
        assertEquals("Breaking Bad", found.get().getTitle());
        assertEquals("Gilligian", found.get().getCreator());
    }

    @Test
    void should_find_all_series() {
        Series series1 = Series.builder()
                .title("Breaking Bad")
                .genres(List.of(GenreEnum.CRIME))
                .creator("Gilligian")
                .startYear(2008)
                .description("Chemistry teacher turns into a drug kingpin")
                .build();

        Series series2 = Series.builder()
                .title("Twin Peaks")
                .genres(List.of(GenreEnum.MYSTERY))
                .creator("Lynch")
                .startYear(1992)
                .description("A murder in the mysterious town of Twin Peaks")
                .build();

        seriesRepository.save(series1);
        seriesRepository.save(series2);

        List<Series> series = seriesRepository.findAll();

        assertEquals(2, series.size());
    }
}
