package org.daniel.moviesandseriestrackermaster.service;

import org.daniel.moviesandseriestrackermaster.enums.GenreEnum;
import org.daniel.moviesandseriestrackermaster.models.Movie;
import org.daniel.moviesandseriestrackermaster.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieService movieService;

    private UUID id;
    private Movie movie;

    @BeforeEach
    void setUp() {
        id = UUID.fromString("11111111-1111-1111-1111-111111111111");
        movie = Movie.builder()
                .id(id)
                .title("Inception")
                .genres(List.of(GenreEnum.SCI_FI))
                .director("Nolan")
                .releaseYear(2010)
                .description("Dreams")
                .duration(148)
                .build();
    }

    @Test
    void GetMovieByIdExists() {
        when(movieRepository.findById(id)).thenReturn(Optional.of(movie));

        Optional<Movie> result = movieService.getMovieById(id);

        assertTrue(result.isPresent());
        assertEquals("Inception", result.get().getTitle());
    }
}
