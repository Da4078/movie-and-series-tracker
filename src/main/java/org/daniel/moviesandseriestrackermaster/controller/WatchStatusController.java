package org.daniel.moviesandseriestrackermaster.controller;

import org.daniel.moviesandseriestrackermaster.dto.StatusDTO;
import org.daniel.moviesandseriestrackermaster.enums.WatchStatusEnum;
import org.daniel.moviesandseriestrackermaster.models.WatchStatus;
import org.daniel.moviesandseriestrackermaster.service.WatchStatusService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/watch-status")
public class WatchStatusController {

    private final WatchStatusService watchStatusService;

    public WatchStatusController(WatchStatusService watchStatusService) {
        this.watchStatusService = watchStatusService;
    }

    @PutMapping("/{userId}")
    public ResponseEntity<WatchStatus> mark(@PathVariable UUID userId, @RequestBody StatusDTO statusDTO){
        WatchStatus watchStatus = watchStatusService.markStatus(userId, statusDTO);
        return ResponseEntity.ok(watchStatus);
    }

    @GetMapping
    public ResponseEntity<List<WatchStatus>> filterByStatus(@RequestParam WatchStatusEnum watchStatusEnum){
        List<WatchStatus> list = watchStatusService.getByStatus(watchStatusEnum);
        return ResponseEntity.ok(list);
    }
}
