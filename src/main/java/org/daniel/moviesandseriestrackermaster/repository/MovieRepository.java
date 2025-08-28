package org.daniel.moviesandseriestrackermaster.repository;

import org.daniel.moviesandseriestrackermaster.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MovieRepository extends JpaRepository<Movie, UUID>, JpaSpecificationExecutor<Movie> {

}
