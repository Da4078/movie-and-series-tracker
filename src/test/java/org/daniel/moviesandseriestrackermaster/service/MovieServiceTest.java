package org.daniel.moviesandseriestrackermaster.service;

import org.daniel.moviesandseriestrackermaster.dto.MovieDTO;
import org.daniel.moviesandseriestrackermaster.enums.GenreEnum;
import org.daniel.moviesandseriestrackermaster.models.Movie;
import org.daniel.moviesandseriestrackermaster.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

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
    void setUp() {
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
    void createMovie(){
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
    void GetMovieById_IsPresent() {
        when(movieRepository.findById(id)).thenReturn(Optional.of(movie));

        Optional<Movie> result = movieService.getMovieById(id);

        assertTrue(result.isPresent());
        assertEquals("Inception", result.get().getTitle());

        verify(movieRepository).findById(id);
    }

    @Test
    void GetMovieById_IsEmpty(){
        when(movieRepository.findById(id)).thenReturn(Optional.empty());
        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> movieService.getMovieById(id));
        assertTrue(exception.getMessage().contains("not found"));
    }

    @Test
    void getMoviesByTitle(){
        when(movieRepository.findByTitleContainingIgnoreCase("incep"))
                .thenReturn(List.of(movie));

        List<Movie> result = movieService.getMovies("incep", null,
                null, "title", "asc");

        assertEquals(1, result.size());
        verify(movieRepository).findByTitleContainingIgnoreCase("incep");
    }

    @Test
    void getMoviesByDirector(){
        when(movieRepository.findByDirector("Nolan"))
                .thenReturn(List.of(movie));

        List<Movie> result = movieService.getMovies(null, null,
                "Nolan", "title", "asc");

        assertEquals(1, result.size());
        verify(movieRepository).findByDirector("Nolan");
    }

    @Test
    void getMoviesByGenre(){
        when(movieRepository.findByGenres(GenreEnum.SCI_FI))
                .thenReturn(List.of(movie));

        List<Movie> result = movieService.getMovies(null, GenreEnum.SCI_FI,
                null, "title", "asc");

        assertEquals(1, result.size());
        verify(movieRepository).findByGenres(GenreEnum.SCI_FI);
    }

    @Test
    void getMovies(){
        when(movieRepository.findAll(any(Sort.class)))
                .thenReturn(List.of(movie));


        List<Movie> result = movieService.getMovies(null, null,
                null, "title", "desc");

        assertEquals(1, result.size());
        verify(movieRepository).findAll(any(Sort.class));
    }

    @Test
    void updateMovie_Exists(){
        when(movieRepository.findById(id)).thenReturn(Optional.of(movie));
        when(movieRepository.save(any(Movie.class))).thenReturn(movie);

        movieDTO.setTitle("The Dark Knight");

        Movie updated = movieService.updateMovie(id, movieDTO);

        assertEquals("The Dark Knight", updated.getTitle());
        verify(movieRepository).save(movie);
    }

    @Test
    void updateMovie_notExists(){
        when(movieRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(IllegalStateException.class, () -> movieService.updateMovie(id, movieDTO));

    }

    @Test
    void deleteMovie(){
        when(movieRepository.existsById(id)).thenReturn(true);

        movieService.deleteMovie(id);

        verify(movieRepository).deleteById(id);
    }

    @Test
    void deleteMovie_notExists(){
        when(movieRepository.existsById(id)).thenReturn(false);

        assertThrows(IllegalStateException.class, () -> movieService.deleteMovie(id));

        verify(movieRepository, never()).deleteById(id);
    }


}