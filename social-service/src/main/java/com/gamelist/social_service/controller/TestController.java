package com.gamelist.social_service.controller;

import com.gamelist.social_service.clients.game.GameDTO;
import com.gamelist.social_service.clients.game.GameServiceClient;
import com.gamelist.social_service.clients.game.HttpResponseModel;
import com.gamelist.social_service.model.HttpResponse;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/test")
@CrossOrigin(origins = "*")
public class TestController {
    private static final Logger log = LoggerFactory.getLogger(PostController.class);
    private final GameServiceClient client;

    @GetMapping
    public ResponseEntity<HttpResponse> getTest(@RequestHeader(name = "userId", required = false) Long userId) {
        log.info("getAllPostsByUser called in getTest with userId: {}", userId);
        Optional<HttpResponseModel> result = client.getGameById(1L);
        Map<String, GameDTO> data = Map.of();
        if (result.isPresent()) {
            data = result.get().data();
        }

        return ResponseEntity.ok(HttpResponse.builder()
                .timeStamp(LocalDateTime.now().toString())
                .data(data)
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .message("Game retrieved successfully")
                .build());
    }
}
