package org.daniel.moviesandseriestrackermaster.controller;

import org.daniel.moviesandseriestrackermaster.dto.MovieDTO;
import org.daniel.moviesandseriestrackermaster.enums.GenreEnum;
import org.daniel.moviesandseriestrackermaster.models.Movie;
import org.daniel.moviesandseriestrackermaster.service.MovieService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping
    public ResponseEntity<Movie> create(@RequestBody MovieDTO movieDTO){
        return ResponseEntity.ok(movieService.createMovie(movieDTO));
    }


    @GetMapping("/{id}")
    public ResponseEntity<Optional<Movie>> findById(@PathVariable UUID id){
        return ResponseEntity.ok(movieService.getMovieById(id));
    }

    @GetMapping("/filter")
    public ResponseEntity<Page<Movie>> filterMovies(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) GenreEnum genreEnum,
            @RequestParam(required = false) String director,
            @RequestParam(defaultValue = "title") String sortBy,
            @RequestParam(defaultValue = "asc") String direction,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        return ResponseEntity.ok(movieService
                .getMovies(title, genreEnum, director, sortBy, direction, page, size));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> update(@PathVariable UUID id, @RequestBody MovieDTO movieDTO){
        return ResponseEntity.ok(movieService.updateMovie(id, movieDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Movie> delete(@PathVariable UUID id){
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }
}
