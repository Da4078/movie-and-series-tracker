package org.daniel.moviesandseriestrackermaster.repository;

import org.daniel.moviesandseriestrackermaster.models.WatchStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface WatchStatusRepository extends JpaRepository<WatchStatus, UUID> {
    Optional<WatchStatus> findByUserIdAndMovieId(UUID userId, UUID movieId);
    Optional<WatchStatus> findByUserIdAndSeriesId(UUID userId, UUID seriesId);
}
