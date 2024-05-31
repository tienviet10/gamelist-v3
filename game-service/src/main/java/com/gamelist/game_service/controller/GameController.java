package com.gamelist.game_service.controller;

import com.gamelist.game_service.dto.GameDTO;
import com.gamelist.game_service.model.HttpResponse;
import com.gamelist.game_service.service.impl.GameServiceImpl;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/games")
@CrossOrigin(origins = "*")
public class GameController {
    private static final Logger log = LoggerFactory.getLogger(GameController.class);
    private final GameServiceImpl gameService;

    @GetMapping("/{gameId}")
    public ResponseEntity<HttpResponse> getGameById(@PathVariable Long gameId) {
        log.info("Fetching game for id: {}", gameId);
        GameDTO game = gameService.getAGame(gameId);
        return ResponseEntity.ok(HttpResponse.builder()
                .timeStamp(LocalDateTime.now().toString())
                .data(Map.of("getGameById", game))
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .build());
    }
}
