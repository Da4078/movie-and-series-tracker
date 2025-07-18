package org.daniel.moviesandseriestrackermaster.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieDTO {
    private String title;
    private List<String> genres;
    private String director;
    private int releaseYear;
    private String description;
    private int duration;
}
