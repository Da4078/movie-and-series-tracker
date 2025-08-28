package org.daniel.moviesandseriestrackermaster.repository;

import org.daniel.moviesandseriestrackermaster.enums.GenreEnum;
import org.daniel.moviesandseriestrackermaster.enums.WatchStatusEnum;
import org.daniel.moviesandseriestrackermaster.models.Movie;
import org.daniel.moviesandseriestrackermaster.models.User;
import org.daniel.moviesandseriestrackermaster.models.WatchStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class WatchStatusRepositoryTest {

    @Autowired
    private WatchStatusRepository watchStatusRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Test
    void should_find_watch_status_by_user_and_enum() {
        User user = userRepository.save(User.builder()
                .email("user@test.com")
                .name("Test User")
                .build());

        Movie movie = movieRepository.save(Movie.builder()
                .title("Inception")
                .director("Nolan")
                .releaseYear(2010)
                .duration(148)
                .genres(List.of(GenreEnum.SCI_FI))
                .build());

        WatchStatus watchStatus = watchStatusRepository.save(WatchStatus.builder()
                .user(user)
                .movie(movie)
                .watchStatusEnum(WatchStatusEnum.WATCHED)
                .build());

        List<WatchStatus> found = watchStatusRepository.findByUserAndWatchStatusEnum(user, WatchStatusEnum.WATCHED);

        assertEquals(1, found.size());
        assertEquals("Inception", found.get(0).getMovie().getTitle());
    }
}
