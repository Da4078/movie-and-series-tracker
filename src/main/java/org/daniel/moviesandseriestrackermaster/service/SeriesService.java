package org.daniel.moviesandseriestrackermaster.service;

import org.daniel.moviesandseriestrackermaster.dto.SeriesDTO;
import org.daniel.moviesandseriestrackermaster.models.Series;
import org.daniel.moviesandseriestrackermaster.repository.SeriesRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SeriesService {

    private final SeriesRepository seriesRepository;

    public SeriesService(SeriesRepository seriesRepository) {
        this.seriesRepository = seriesRepository;
    }

    public Series createSeries(SeriesDTO seriesDTO){
        Series series = Series.builder()
                .title(seriesDTO.getTitle())
                .genre(seriesDTO.getGenres())
                .creator(seriesDTO.getCreator())
                .startYear(seriesDTO.getStartYear())
                .description(seriesDTO.getDescription())
                .build();
        return seriesRepository.save(series);
    }

    public Series updateSeries(UUID id, SeriesDTO seriesDTO){
        Series existing = seriesRepository.findById(id).orElseThrow(()-> new IllegalStateException("Series not found: " + id));
        existing.setTitle(seriesDTO.getTitle());
        existing.setGenre(seriesDTO.getGenres());
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
