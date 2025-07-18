package org.daniel.moviesandseriestrackermaster.service;

import org.daniel.moviesandseriestrackermaster.dto.MovieDTO;
import org.daniel.moviesandseriestrackermaster.models.Movie;
import org.daniel.moviesandseriestrackermaster.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MovieService {
    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Movie createMovie(MovieDTO movieDTO){
        Movie movie = Movie.builder()
                .title(movieDTO.getTitle())
                .genre(movieDTO.getGenres())
                .director(movieDTO.getDirector())
                .releaseYear(movieDTO.getReleaseYear())
                .description(movieDTO.getDescription())
                .duration(movieDTO.getDuration())
                .build();
        return movieRepository.save(movie);
    }

    public Movie updateMovie(UUID id, MovieDTO movieDTO){
        Movie existing = movieRepository.findById(id).orElseThrow(()-> new IllegalStateException("Movie not found: " + id));
        existing.setTitle(movieDTO.getTitle());
        existing.setGenre(movieDTO.getGenres());
        existing.setDirector(movieDTO.getDirector());
        existing.setReleaseYear(movieDTO.getReleaseYear());
        existing.setDescription(movieDTO.getDescription());
        existing.setDuration(movieDTO.getDuration());

        return movieRepository.save(existing);
    }

    public void deleteMovie(UUID id){
        if(!movieRepository.existsById(id)){
            throw new IllegalStateException("Movie not found" + id);
        }
        movieRepository.deleteById(id);
    }

}
