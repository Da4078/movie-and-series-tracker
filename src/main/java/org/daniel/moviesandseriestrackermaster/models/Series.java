package org.daniel.moviesandseriestrackermaster.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "series")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Series {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String title;

    @ElementCollection
    @CollectionTable(name = "series_genres", joinColumns = @JoinColumn(name="series_id"))
    @Column(name="genre")
    private List<String> genre;

    @Column(nullable = false)
    private String creator;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "start_year", nullable = false)
    private int startYear;

    @OneToMany(mappedBy = "series", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<WatchStatus> watchStatuses = new ArrayList<>();

}
