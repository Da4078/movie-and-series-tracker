        package org.daniel.moviesandseriestrackermaster.controller;

        import org.daniel.moviesandseriestrackermaster.dto.WatchStatusDTO;
        import org.daniel.moviesandseriestrackermaster.models.WatchStatus;
        import org.daniel.moviesandseriestrackermaster.service.WatchStatusService;
        import org.springframework.http.ResponseEntity;
        import org.springframework.web.bind.annotation.*;

        import java.util.UUID;

        @RestController
        @RequestMapping("/api/watch-status")
        public class WatchStatusController {

            private final WatchStatusService watchStatusService;

            public WatchStatusController(WatchStatusService watchStatusService) {
                this.watchStatusService = watchStatusService;
            }

                @PutMapping("/{userId}/{contentId}")
                public ResponseEntity<WatchStatus> markStatus(
                        @PathVariable UUID userId,
                        @PathVariable UUID contentId,
                        @RequestBody WatchStatusDTO watchStatusDTO){
                    return ResponseEntity.ok(watchStatusService
                            .markStatus(userId, contentId, watchStatusDTO.getWatchStatusEnum()));
                }
        }
