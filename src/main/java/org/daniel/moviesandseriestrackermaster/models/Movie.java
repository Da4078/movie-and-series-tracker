package org.daniel.moviesandseriestrackermaster.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.daniel.moviesandseriestrackermaster.enums.ContentTypeEnum;
import org.daniel.moviesandseriestrackermaster.enums.GenreEnum;
import org.daniel.moviesandseriestrackermaster.enums.WatchStatusEnum;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "movie")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String title;

    @CollectionTable(name = "movie_genres", joinColumns = @JoinColumn(name="movie_id"))
    @Column(name="genre", nullable = false)
    @Enumerated(EnumType.STRING)
    private List<GenreEnum> genres;

    @Column(nullable = false)
    private String director;

    @Column(name = "release_year", nullable = false)
    private int releaseYear;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private int duration;

    @Enumerated(EnumType.STRING)
    private final ContentTypeEnum contentType = ContentTypeEnum.MOVIE;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<WatchStatus> watchStatuses;
}
