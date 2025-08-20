package org.daniel.moviesandseriestrackermaster.service;

import org.daniel.moviesandseriestrackermaster.dto.SeriesDTO;
import org.daniel.moviesandseriestrackermaster.enums.GenreEnum;
import org.daniel.moviesandseriestrackermaster.models.Series;
import org.daniel.moviesandseriestrackermaster.repository.SeriesRepository;
import org.springframework.data.domain.Sort;
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
        return seriesRepository.findById(id);
    }

    public List<Series> getSeries(String title, GenreEnum genreEnum, String creator,
                                  String sortBy, String direction){
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy)
                .descending() : Sort.by(sortBy).ascending();

        if(title != null){
            return seriesRepository.findByTitleContainingIgnoreCase(title);
        } else if (creator != null){
            return seriesRepository.findByCreator(creator);
        } else if (genreEnum != null){
            return seriesRepository.findByGenres(genreEnum);
        } else{
            return seriesRepository.findAll(sort);
        }
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
