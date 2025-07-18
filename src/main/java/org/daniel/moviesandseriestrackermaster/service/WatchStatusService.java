package org.daniel.moviesandseriestrackermaster.service;

import org.daniel.moviesandseriestrackermaster.dto.StatusDTO;
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

import java.util.List;
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

    public WatchStatus markStatus(UUID userId, StatusDTO statusDTO){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found" + userId));

        WatchStatus watchStatus = WatchStatus.builder()
                .user(user)
                .status(statusDTO.getStatus())
                .build();
        if("MOVIE".equalsIgnoreCase(statusDTO.getContentType())){
            Movie movie = movieRepository.findById(statusDTO.getContentId())
                    .orElseThrow(() -> new IllegalArgumentException("User not found" + userId));
            watchStatus.setMovie(movie);
        } else {
            Series series = seriesRepository.findById(statusDTO.getContentId())
                    .orElseThrow(() -> new IllegalArgumentException("User not found" + userId));
            watchStatus.setSeries(series);
        }

        return watchStatusRepository.save(watchStatus);
    }

    public List<WatchStatus> getByStatus(WatchStatusEnum watchStatusEnum){
        return watchStatusRepository.findByStatus(watchStatusEnum);
    }

}
