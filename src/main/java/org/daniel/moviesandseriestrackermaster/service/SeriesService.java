package org.daniel.moviesandseriestrackermaster.service;

import org.daniel.moviesandseriestrackermaster.dto.SeriesDTO;
import org.daniel.moviesandseriestrackermaster.enums.GenreEnum;
import org.daniel.moviesandseriestrackermaster.models.Movie;
import org.daniel.moviesandseriestrackermaster.models.Series;
import org.daniel.moviesandseriestrackermaster.repository.SeriesRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SeriesService {

    private final SeriesRepository seriesRepository;

    public SeriesService(SeriesRepository seriesRepository) {
        this.seriesRepository = seriesRepository;
    }

    public Optional<Series> getSeriesById(UUID id){
        Optional<Series> series = seriesRepository.findById(id);
        if(series.isEmpty()){
            throw new IllegalStateException("Series with id: " + id + "not found");
        }
        return series;
    }

    public Page<Series> getSeries(String title, GenreEnum genreEnum, String creator,
                                 String sortBy, String direction, int page, int size){
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy)
                .descending() : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Specification<Series> spec = null;

        if (title != null && !title.isBlank()) {
            Specification<Series> titleSpec = (root, query, cb) ->
                    cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%");
            spec = (spec == null) ? titleSpec : spec.and(titleSpec);
        }

        if (creator != null && !creator.isBlank()) {
            Specification<Series> creatorSpec = (root, query, cb) ->
                    cb.equal(root.get("director"), creator);
            spec = (spec == null) ? creatorSpec : spec.and(creatorSpec);
        }

        if (genreEnum != null) {
            Specification<Series> genreSpec = (root, query, cb) ->
                    cb.equal(root.get("genres"), genreEnum);
            spec = (spec == null) ? genreSpec : spec.and(genreSpec);
        }

        return seriesRepository.findAll(spec, pageable);
    }

    public Series createSeries(SeriesDTO seriesDTO){
        Series series = Series.builder()
                .title(seriesDTO.getTitle())
                .genres(seriesDTO.getGenres())
                .creator(seriesDTO.getCreator())
                .startYear(seriesDTO.getStartYear())
                .description(seriesDTO.getDescription())
                .build();
        return seriesRepository.save(series);
    }

    public Series updateSeries(UUID id, SeriesDTO seriesDTO){
        Series existing = seriesRepository.findById(id)
                .orElseThrow(()-> new IllegalStateException("Series not found: " + id));
        existing.setTitle(seriesDTO.getTitle());
        existing.setGenres(seriesDTO.getGenres());
        existing.setCreator(seriesDTO.getCreator());
        existing.setStartYear(seriesDTO.getStartYear());
        existing.setDescription(seriesDTO.getDescription());

        return seriesRepository.save(existing);
    }

    public void deleteSeries(UUID id){
        if(!seriesRepository.existsById(id)){
            throw new IllegalStateException("Series not found" + id);
        }
        seriesRepository.deleteById(id);
    }


}
