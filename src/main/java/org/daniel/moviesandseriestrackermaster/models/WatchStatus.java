package org.daniel.moviesandseriestrackermaster.models;

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
    @JoinColumn(name = "user_id", nullable = true)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id", nullable = true)
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "series_id", nullable = true)
    private Series series;

    @Enumerated(EnumType.STRING)
    private WatchStatusEnum watchStatusEnum;


}
