package org.daniel.moviesandseriestrackermaster.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.daniel.moviesandseriestrackermaster.enums.WatchStatusEnum;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WatchStatusDTO {
    private WatchStatusEnum watchStatusEnum;
}
