package org.daniel.moviesandseriestrackermaster.repository;

import org.daniel.moviesandseriestrackermaster.enums.WatchStatusEnum;
import org.daniel.moviesandseriestrackermaster.models.WatchStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface WatchStatusRepository extends JpaRepository<WatchStatus, UUID> {
    List<WatchStatus> findByStatus(WatchStatusEnum status);
}
