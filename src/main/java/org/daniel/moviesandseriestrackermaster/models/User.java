package org.daniel.moviesandseriestrackermaster.models;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="app_user")
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    @Schema(type = "string", format = "email", example = "user@example.com")
    @Email
    private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @ToString.Exclude
    @JsonManagedReference
    private List<WatchStatus> watchStatuses = new ArrayList<>();

}
