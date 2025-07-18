package org.daniel.moviesandseriestrackermaster.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.Year;
import java.util.ArrayList;
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
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String title;

    @ElementCollection
    @CollectionTable(name = "movie_genres", joinColumns = @JoinColumn(name="movie_id"))
    @Column(name="genre")
    private List<String> genre;

    @Column(nullable = false)
    private String director;

    @Column(name = "release_year", nullable = false)
    private int releaseYear;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private int duration;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<WatchStatus> watchStatuses = new ArrayList<>();
}
