package com.gamelist.game_service.controller;

import com.gamelist.game_service.model.HttpResponse;
import com.gamelist.game_service.projection.StatusUpdateView;
import com.gamelist.game_service.service.StatusUpdateService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/statusupdates")
@CrossOrigin(origins = "*")
public class StatusUpdateController {
    private static final Logger log = LoggerFactory.getLogger(StatusUpdateController.class);
    private final StatusUpdateService statusUpdateService;

    @GetMapping
    public ResponseEntity<HttpResponse> getAllStatusUpdatesByUser(@RequestHeader(name = "userId") Long userId) {
        log.info("getAllStatusUpdatesByUser called with userId: {}", userId);
        List<StatusUpdateView> statusUpdateViewList = statusUpdateService.findAllStatusUpdatesByUserId(userId);

        return ResponseEntity.ok(HttpResponse.builder()
                .timeStamp(LocalDateTime.now().toString())
                .data(Map.of("statusUpdates", statusUpdateViewList))
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .message("Status updates retrieved successfully")
                .build());
    }
}
