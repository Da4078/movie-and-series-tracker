package org.daniel.moviesandseriestrackermaster.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.daniel.moviesandseriestrackermaster.enums.WatchStatusEnum;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StatusDTO {
    private UUID contentId;
    private String contentType;
    private WatchStatusEnum status;
}
