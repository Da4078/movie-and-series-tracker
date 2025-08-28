package org.daniel.moviesandseriestrackermaster.controller;

import org.daniel.moviesandseriestrackermaster.dto.MovieDTO;
import org.daniel.moviesandseriestrackermaster.enums.GenreEnum;
import org.daniel.moviesandseriestrackermaster.models.Movie;
import org.daniel.moviesandseriestrackermaster.service.MovieService;
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
public class MovieControllerTest {
    @Mock
    private MovieService movieService;

    @InjectMocks
    private MovieController movieController;

    private UUID movieId;
    private Movie movie;
    private MovieDTO movieDTO;

    @BeforeEach
    void set_up(){
        movieId = UUID.fromString("578ad8cf-fcd2-4e43-8deb-926d496f1996");
        movieDTO = new MovieDTO("Inception",
                List.of(GenreEnum.SCI_FI),
                "Nolan",
                2010,
                "Dreams",
                148);

        movie = Movie.builder()
                .id(movieId)
                .title("Inception")
                .genres(List.of(GenreEnum.SCI_FI))
                .director("Nolan")
                .releaseYear(2010)
                .description("Dreams")
                .duration(148)
                .build();
    }

    @Test
    void should_create_movie(){
        when(movieService.createMovie(movieDTO)).thenReturn(movie);

        ResponseEntity<Movie> response = movieController.create(movieDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(movie, response.getBody());
        verify(movieService).createMovie(movieDTO);
    }

    @Test
    void should_find_movie_by_id() {
        when(movieService.getMovieById(movieId)).thenReturn(Optional.of(movie));

        ResponseEntity<Optional<Movie>> response = movieController.findById(movieId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isPresent());
        assertEquals(movie, response.getBody().get());
        verify(movieService).getMovieById(movieId);
    }

    @Test
    void should_filter_movies() {
        Page<Movie> page = new PageImpl<>(List.of(movie));
        when(movieService.getMovies("Inception", GenreEnum.SCI_FI, "Nolan",
                "title", "asc", 0, 10)).thenReturn(page);

        ResponseEntity<Page<Movie>> response = movieController.filterMovies(
                "Inception", GenreEnum.SCI_FI, "Nolan",
                "title", "asc", 0, 10);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getTotalElements());
        verify(movieService).getMovies("Inception", GenreEnum.SCI_FI, "Nolan",
                "title", "asc", 0, 10);
    }

    @Test
    void should_update_movie() {
        when(movieService.updateMovie(movieId, movieDTO)).thenReturn(movie);

        ResponseEntity<Movie> response = movieController.update(movieId, movieDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(movie, response.getBody());
        verify(movieService).updateMovie(movieId, movieDTO);
    }

    @Test
    void should_delete_movie() {
        doNothing().when(movieService).deleteMovie(movieId);

        ResponseEntity<Movie> response = movieController.delete(movieId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(movieService).deleteMovie(movieId);
    }
}
