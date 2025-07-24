package org.daniel.moviesandseriestrackermaster.service;

import org.daniel.moviesandseriestrackermaster.dto.WatchStatusDTO;
import org.daniel.moviesandseriestrackermaster.enums.WatchStatusEnum;
import org.daniel.moviesandseriestrackermaster.models.Movie;
import org.daniel.moviesandseriestrackermaster.models.Series;
import org.daniel.moviesandseriestrackermaster.models.User;
import org.daniel.moviesandseriestrackermaster.models.WatchStatus;
import org.daniel.moviesandseriestrackermaster.repository.MovieRepository;
import org.daniel.moviesandseriestrackermaster.repository.SeriesRepository;
import org.daniel.moviesandseriestrackermaster.repository.UserRepository;
import org.daniel.moviesandseriestrackermaster.repository.WatchStatusRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class WatchStatusService {

    private final WatchStatusRepository watchStatusRepository;
    private final SeriesRepository seriesRepository;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;

    public WatchStatusService(WatchStatusRepository watchStatusRepository, SeriesRepository seriesRepository, MovieRepository movieRepository, UserRepository userRepository) {
        this.watchStatusRepository = watchStatusRepository;
        this.seriesRepository = seriesRepository;
        this.movieRepository = movieRepository;
        this.userRepository = userRepository;
    }

    public WatchStatus markStatus(UUID userId, UUID contentId, WatchStatusEnum watchStatusEnum){
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new IllegalArgumentException("User not found"));

        Optional<Movie> movie = movieRepository.findById(contentId);
        Optional<Series> series = seriesRepository.findById(contentId);

        WatchStatus watchStatus = new WatchStatus();


        if(movie.isPresent()){
            watchStatus.setMovie(movie.get());
        } else if(series.isPresent()){
            watchStatus.setSeries(series.get());
        } else {
            throw new IllegalArgumentException("Content not found!");
        }
        watchStatus.setUser(user);
        watchStatus.setWatchStatusEnum(watchStatusEnum);

        return watchStatusRepository.save(watchStatus);
    }
}
