package org.daniel.moviesandseriestrackermaster.service;

import org.daniel.moviesandseriestrackermaster.dto.MovieDTO;
import org.daniel.moviesandseriestrackermaster.enums.GenreEnum;
import org.daniel.moviesandseriestrackermaster.models.Movie;
import org.daniel.moviesandseriestrackermaster.repository.MovieRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MovieService {
    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Optional<Movie> getMovieById(UUID id){
        Optional<Movie> movie = movieRepository.findById(id);
        if(movie.isEmpty()){
            throw new IllegalStateException("Movie with id: " + id + "not found");
        }
        return movie;
    }

    public List<Movie> getMovies(String title, GenreEnum genreEnum, String director,
                                  String sortBy, String direction){
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy)
                .descending() : Sort.by(sortBy).ascending();    

        if(title != null){
            return movieRepository.findByTitleContainingIgnoreCase(title);
        } else if (director != null){
            return movieRepository.findByDirector(director);
        } else if (genreEnum != null){
            return movieRepository.findByGenres(genreEnum);
        } else{
            return movieRepository.findAll(sort);
        }
    }

    public Movie createMovie(MovieDTO movieDTO){
        Movie movie = Movie.builder()
                .title(movieDTO.getTitle())
                .genres(movieDTO.getGenres())
                .director(movieDTO.getDirector())
                .releaseYear(movieDTO.getReleaseYear())
                .description(movieDTO.getDescription())
                .duration(movieDTO.getDuration())
                .build();
        return movieRepository.save(movie);
    }

    public Movie updateMovie(UUID id, MovieDTO movieDTO){
        Movie existing = movieRepository.findById(id)
                .orElseThrow(()-> new IllegalStateException("Movie not found: " + id));
        existing.setTitle(movieDTO.getTitle());
        existing.setGenres(movieDTO.getGenres());
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
