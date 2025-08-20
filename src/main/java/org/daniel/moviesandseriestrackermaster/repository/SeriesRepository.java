package org.daniel.moviesandseriestrackermaster.repository;

import org.daniel.moviesandseriestrackermaster.enums.GenreEnum;
import org.daniel.moviesandseriestrackermaster.models.Series;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SeriesRepository extends JpaRepository<Series, UUID>, JpaSpecificationExecutor<Series> {
    List<Series> findByTitleContainingIgnoreCase(String title);
    List<Series> findByCreator(String creator);
    List<Series> findByGenres(GenreEnum genreEnum);

}
