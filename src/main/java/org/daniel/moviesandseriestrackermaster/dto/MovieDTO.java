package org.daniel.moviesandseriestrackermaster.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.daniel.moviesandseriestrackermaster.enums.GenreEnum;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieDTO {

    private UUID id;
    private String title;
    private List<GenreEnum> genres;
    private String director;
    private int releaseYear;
    private String description;
    private int duration;

}
