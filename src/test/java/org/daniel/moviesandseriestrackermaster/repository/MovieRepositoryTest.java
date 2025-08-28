package org.daniel.moviesandseriestrackermaster.repository;

import org.daniel.moviesandseriestrackermaster.enums.GenreEnum;
import org.daniel.moviesandseriestrackermaster.models.Movie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class MovieRepositoryTest {

    @Autowired
    private MovieRepository movieRepository;

    @Test
    void should_save_and_find_movie_by_id(){
        Movie movie = Movie.builder()
                .title("Inception")
                .director("Christopher Nolan")
                .releaseYear(2010)
                .description("A mind-bending thriller")
                .duration(148)
                .genres(List.of(GenreEnum.SCI_FI))
                .build();

        Movie saved = movieRepository.save(movie);

        Optional<Movie> found = movieRepository.findById(saved.getId());

        assertTrue(found.isPresent());
        assertEquals("Inception", found.get().getTitle());
        assertEquals("Christopher Nolan", found.get().getDirector());
    }

    @Test
    void should_find_all_movies() {
        Movie movie1 = Movie.builder()
                .title("The Dark Knight")
                .director("Christopher Nolan")
                .releaseYear(2008)
                .description("Batman vs Joker")
                .duration(152)
                .genres(List.of(GenreEnum.ACTION))
                .build();

        Movie movie2 = Movie.builder()
                .title("Interstellar")
                .director("Christopher Nolan")
                .releaseYear(2014)
                .description("Exploring space and time")
                .duration(169)
                .genres(List.of(GenreEnum.SCI_FI))
                .build();

        movieRepository.save(movie1);
        movieRepository.save(movie2);

        List<Movie> movies = movieRepository.findAll();

        assertEquals(2, movies.size());
    }
}
