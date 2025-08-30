package org.daniel.moviesandseriestrackermaster.controller;

import org.daniel.moviesandseriestrackermaster.dto.SeriesDTO;
import org.daniel.moviesandseriestrackermaster.enums.GenreEnum;
import org.daniel.moviesandseriestrackermaster.models.Series;
import org.daniel.moviesandseriestrackermaster.service.SeriesService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/series")
public class SeriesController {

    private final SeriesService seriesService;

    public SeriesController(SeriesService seriesService) {
        this.seriesService = seriesService;
    }

    @PostMapping
    public ResponseEntity<Series> create(@RequestBody SeriesDTO seriesDTO){
        return ResponseEntity.ok(seriesService.createSeries(seriesDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Series> findById(@PathVariable UUID id){
        return seriesService.getSeriesById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/filter")
    public ResponseEntity<Page<Series>> filterSeries(
            @RequestParam(required = false) String title,
            @RequestParam(required = false)GenreEnum genreEnum,
            @RequestParam(required = false) String creator,
            @RequestParam(defaultValue = "title") String sortBy,
            @RequestParam(defaultValue = "asc") String direction,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
            ){
        return ResponseEntity.ok(seriesService
                .getSeries(title, genreEnum, creator, sortBy, direction,  page, size));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Series> update(@PathVariable UUID id, @RequestBody SeriesDTO seriesDTO){
        return ResponseEntity.ok(seriesService.updateSeries(id, seriesDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Series> delete(@PathVariable UUID id){
        seriesService.deleteSeries(id);
        return ResponseEntity.noContent().build();
    }

}
