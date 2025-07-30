package org.daniel.moviesandseriestrackermaster.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.daniel.moviesandseriestrackermaster.enums.WatchStatusEnum;

import java.util.UUID;

@Entity
@Table(name = "watch_status")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WatchStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    @JsonManagedReference
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "series_id")
    @JsonManagedReference
    private Series series;

    @Enumerated(EnumType.STRING)
    private WatchStatusEnum watchStatusEnum;
    
}
