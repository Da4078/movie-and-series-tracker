package org.daniel.moviesandseriestrackermaster.controller;

import org.daniel.moviesandseriestrackermaster.dto.SeriesDTO;
import org.daniel.moviesandseriestrackermaster.models.Movie;
import org.daniel.moviesandseriestrackermaster.models.Series;
import org.daniel.moviesandseriestrackermaster.service.SeriesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
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
        Series created = seriesService.createSeries(seriesDTO);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<Series>> getAll(){
        return ResponseEntity.ok(seriesService.getAllSeries());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Series>> findById(@PathVariable UUID id){
        return ResponseEntity.ok(seriesService.getSeriesById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Series> update(@PathVariable UUID id, @RequestBody SeriesDTO seriesDTO){
        Series updated = seriesService.updateSeries(id, seriesDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Series> delete(@PathVariable UUID id){
        seriesService.deleteSeries(id);
        return ResponseEntity.noContent().build();
    }

}
