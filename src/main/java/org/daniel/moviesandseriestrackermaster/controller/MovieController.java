package org.daniel.moviesandseriestrackermaster.controller;

import org.daniel.moviesandseriestrackermaster.dto.MovieDTO;
import org.daniel.moviesandseriestrackermaster.dto.SeriesDTO;
import org.daniel.moviesandseriestrackermaster.models.Movie;
import org.daniel.moviesandseriestrackermaster.models.Series;
import org.daniel.moviesandseriestrackermaster.service.MovieService;
import org.daniel.moviesandseriestrackermaster.service.SeriesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/movie")
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping
    public ResponseEntity<Movie> create(@RequestBody MovieDTO movieDTO){
        Movie created = movieService.createMovie(movieDTO);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> update(@PathVariable UUID id, @RequestBody MovieDTO movieDTO){
        Movie updated = movieService.updateMovie(id, movieDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Movie> delete(@PathVariable UUID id){
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }
}
