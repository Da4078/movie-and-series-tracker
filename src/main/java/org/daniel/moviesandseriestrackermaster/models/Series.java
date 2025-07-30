package org.daniel.moviesandseriestrackermaster.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.daniel.moviesandseriestrackermaster.enums.ContentTypeEnum;
import org.daniel.moviesandseriestrackermaster.enums.GenreEnum;
import org.daniel.moviesandseriestrackermaster.enums.WatchStatusEnum;

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
    private List<GenreEnum> genres;

    @Column(nullable = false)
    private String creator;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "start_year", nullable = false)
    private int startYear;

    @Enumerated(EnumType.STRING)
    private final ContentTypeEnum contentType = ContentTypeEnum.SERIES;

    @OneToMany(mappedBy = "series", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<WatchStatus> watchStatuses;
}
