package org.daniel.moviesandseriestrackermaster.service;

import org.daniel.moviesandseriestrackermaster.enums.WatchStatusEnum;
import org.daniel.moviesandseriestrackermaster.models.Movie;
import org.daniel.moviesandseriestrackermaster.models.Series;
import org.daniel.moviesandseriestrackermaster.models.User;
import org.daniel.moviesandseriestrackermaster.models.WatchStatus;
import org.daniel.moviesandseriestrackermaster.repository.MovieRepository;
import org.daniel.moviesandseriestrackermaster.repository.SeriesRepository;
import org.daniel.moviesandseriestrackermaster.repository.UserRepository;
import org.daniel.moviesandseriestrackermaster.repository.WatchStatusRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WatchStatusServiceTest {

    @Mock
    private WatchStatusRepository watchStatusRepository;

    @Mock
    private SeriesRepository seriesRepository;

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private WatchStatusService watchStatusService;

    private UUID userId;
    private UUID contentId;
    private User user;
    private Movie movie;
    private Series series;

    @BeforeEach
    void set_up(){
        userId = UUID.fromString("578ad8cf-fcd2-4e43-8deb-926d496f1996");
        contentId = UUID.fromString("578ad8cf-fcd2-4e43-8deb-926d496f1996");

        user = new User();
        user.setId(userId);

        movie = new Movie();
        movie.setId(contentId);

        series = new Series();
        series.setId(contentId);
    }

    @Test
    void should_mark_movie_status() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(movieRepository.findById(contentId)).thenReturn(Optional.of(movie));
        when(seriesRepository.findById(contentId)).thenReturn(Optional.empty());

        WatchStatus expected = new WatchStatus();
        expected.setUser(user);
        expected.setMovie(movie);
        expected.setWatchStatusEnum(WatchStatusEnum.WATCHED);

        when(watchStatusRepository.save(any(WatchStatus.class))).thenReturn(expected);

        WatchStatus result = watchStatusService.markStatus(userId, contentId, WatchStatusEnum.WATCHED);

        assertNotNull(result);
        assertEquals(user, result.getUser());
        assertEquals(movie, result.getMovie());
        assertEquals(WatchStatusEnum.WATCHED, result.getWatchStatusEnum());

        verify(watchStatusRepository).save(any(WatchStatus.class));
    }

    @Test
    void should_mark_series_status() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(movieRepository.findById(contentId)).thenReturn(Optional.empty());
        when(seriesRepository.findById(contentId)).thenReturn(Optional.of(series));

        WatchStatus expected = new WatchStatus();
        expected.setUser(user);
        expected.setSeries(series);
        expected.setWatchStatusEnum(WatchStatusEnum.WATCHED);

        when(watchStatusRepository.save(any(WatchStatus.class))).thenReturn(expected);

        WatchStatus result = watchStatusService.markStatus(userId, contentId, WatchStatusEnum.WATCHED);

        assertNotNull(result);
        assertEquals(user, result.getUser());
        assertEquals(series, result.getSeries());
        assertEquals(WatchStatusEnum.WATCHED, result.getWatchStatusEnum());

        verify(watchStatusRepository).save(any(WatchStatus.class));
    }

    @Test
    void should_throw_when_marking_user_not_found() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () ->
                watchStatusService.markStatus(userId, contentId, WatchStatusEnum.WATCHING));
    }

    @Test
    void should_throw_when_content_not_found(){
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(movieRepository.findById(contentId)).thenReturn(Optional.empty());
        when(seriesRepository.findById(contentId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () ->
                watchStatusService.markStatus(userId, contentId, WatchStatusEnum.WATCHED));
    }

    @Test
    void should_return_statuses(){
        WatchStatus status = new WatchStatus();
        status.setUser(user);
        status.setWatchStatusEnum(WatchStatusEnum.WATCHED);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(watchStatusRepository.findByUserAndWatchStatusEnum(user, WatchStatusEnum.WATCHED))
                .thenReturn(List.of(status));

        List<WatchStatus> result = watchStatusService.filterByStatus(userId, WatchStatusEnum.WATCHED);

        assertEquals(1, result.size());
        assertEquals(WatchStatusEnum.WATCHED, result.get(0).getWatchStatusEnum());

        verify(watchStatusRepository).findByUserAndWatchStatusEnum(user, WatchStatusEnum.WATCHED);
    }

    @Test
    void should_throw_when_filtering_user_not_found() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () ->
                watchStatusService.filterByStatus(userId, WatchStatusEnum.WATCHING));
    }

}
