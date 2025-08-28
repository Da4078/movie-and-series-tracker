package org.daniel.moviesandseriestrackermaster.service;

import org.daniel.moviesandseriestrackermaster.dto.MovieDTO;
import org.daniel.moviesandseriestrackermaster.enums.GenreEnum;
import org.daniel.moviesandseriestrackermaster.models.Movie;
import org.daniel.moviesandseriestrackermaster.repository.MovieRepository;
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
class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieService movieService;

    private UUID id;
    private Movie movie;
    private MovieDTO movieDTO;

    @BeforeEach
    void set_up() {
        id = UUID.fromString("578ad8cf-fcd2-4e43-8deb-926d496f1996");
        movie = Movie.builder()
                .id(id)
                .title("Inception")
                .genres(List.of(GenreEnum.SCI_FI))
                .director("Nolan")
                .releaseYear(2010)
                .description("Dreams")
                .duration(148)
                .build();
        movieDTO = new MovieDTO();
        movieDTO.setTitle("Inception");
        movieDTO.setGenres(List.of(GenreEnum.SCI_FI));
        movieDTO.setDirector("Nolan");
        movieDTO.setReleaseYear(2010);
        movieDTO.setDescription("Dreams");
        movieDTO.setDuration(148);

    }

    @Test
    void should_create_movie(){
        when(movieRepository.save(any(Movie.class))).thenReturn(movie);
        Movie result = movieService.createMovie(movieDTO);

        assertNotNull(result);
        assertEquals("Inception", result.getTitle());

        ArgumentCaptor<Movie> captor = ArgumentCaptor.forClass(Movie.class);
        verify(movieRepository).save(captor.capture());
        Movie saved = captor.getValue();
        assertEquals("Inception", saved.getTitle());
        assertEquals("Nolan", saved.getDirector());
    }

    @Test
    void should_get_movie_by_id() {
        when(movieRepository.findById(id)).thenReturn(Optional.of(movie));

        Optional<Movie> result = movieService.getMovieById(id);

        assertTrue(result.isPresent());
        assertEquals("Inception", result.get().getTitle());

        verify(movieRepository).findById(id);
    }

    @Test
    void should_not_get_movie_by_id(){
        when(movieRepository.findById(id)).thenReturn(Optional.empty());
        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> movieService.getMovieById(id));
        assertTrue(exception.getMessage().contains("not found"));
    }

    @Test
    void should_get_movies_with_filters() {
        when(movieRepository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(movie)));

        Page<Movie> result = movieService.getMovies(
                "incep", GenreEnum.SCI_FI, "Nolan", "title", "asc", 0, 10);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals("Inception", result.getContent().get(0).getTitle());

        verify(movieRepository).findAll(any(Specification.class), any(Pageable.class));
    }

    @Test
    void should_get_movies_without_filters() {

        when(movieRepository.findAll(ArgumentMatchers.<Specification<Movie>>any(), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(movie)));

        Page<Movie> result = movieService.getMovies(
                null, null, null, "title", "desc", 0, 10);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());

        verify(movieRepository).findAll(ArgumentMatchers.<Specification<Movie>>any(), any(Pageable.class));
    }

    @Test
    void should_update_movie(){
        when(movieRepository.findById(id)).thenReturn(Optional.of(movie));
        when(movieRepository.save(any(Movie.class))).thenReturn(movie);

        movieDTO.setTitle("The Dark Knight");

        Movie updated = movieService.updateMovie(id, movieDTO);

        assertEquals("The Dark Knight", updated.getTitle());
        verify(movieRepository).save(movie);
    }

    @Test
    void should_not_update_movie(){
        when(movieRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(IllegalStateException.class, () -> movieService.updateMovie(id, movieDTO));

    }

    @Test
    void should_delete_movie(){
        when(movieRepository.existsById(id)).thenReturn(true);

        movieService.deleteMovie(id);

        verify(movieRepository).deleteById(id);
    }

    @Test
    void should_not_delete_movie(){
        when(movieRepository.existsById(id)).thenReturn(false);

        assertThrows(IllegalStateException.class, () -> movieService.deleteMovie(id));

        verify(movieRepository, never()).deleteById(id);
    }


}