package org.daniel.moviesandseriestrackermaster.service;

import org.daniel.moviesandseriestrackermaster.dto.SeriesDTO;
import org.daniel.moviesandseriestrackermaster.models.Series;
import org.daniel.moviesandseriestrackermaster.repository.SeriesRepository;
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

    public List<Series> getAllSeries(){
        return seriesRepository.findAll();
    }
    public Optional<Series> getSeriesById(UUID id){
        Optional<Series> series = seriesRepository.findById(id);
        if(series.isEmpty()){
            throw new IllegalStateException("Series with id: " + id + "not found");
        }
        return seriesRepository.findById(id);
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

    //TODO: Instead of using IllegalStateException we not some like NotFoundException a custom one.
    // You can see on google how to make exceptions out of https code like 404 not found
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
