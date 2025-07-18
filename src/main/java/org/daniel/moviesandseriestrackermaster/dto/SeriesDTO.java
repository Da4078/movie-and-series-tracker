package org.daniel.moviesandseriestrackermaster.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SeriesDTO {
    private String title;
    private List<String> genres;
    private String creator;
    private int startYear;
    private String description;
}
