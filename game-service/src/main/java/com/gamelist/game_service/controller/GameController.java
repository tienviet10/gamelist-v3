package com.gamelist.game_service.controller;

import com.gamelist.game_service.dto.GameDTO;
import com.gamelist.game_service.model.GameQueryFilters;
import com.gamelist.game_service.model.HttpResponse;
import com.gamelist.game_service.service.impl.GameServiceImpl;
import java.time.LocalDateTime;
import java.util.List;
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
// @CrossOrigin(origins = "*")
public class GameController {
    private static final Logger log = LoggerFactory.getLogger(GameController.class);
    private final GameServiceImpl gameService;

    @PostMapping
    public ResponseEntity<HttpResponse> getGames(
            @RequestBody(required = false) GameQueryFilters gameQueryFilters,
            @RequestHeader(name = "userId", required = false) Long userId
            //            @RequestHeader(name = "email", required = false) String email
            ) {
        log.info("getGames called with userId: {}", userId);
        List<GameDTO> games = gameService.getAllGames(gameQueryFilters, userId);
        return ResponseEntity.ok(HttpResponse.builder()
                .timeStamp(LocalDateTime.now().toString())
                .data(Map.of("games", games))
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .build());
    }

    @GetMapping("/{gameId}")
    public ResponseEntity<HttpResponse> getGameById(@PathVariable Long gameId) {
        log.info("getGameById called with gameId: {}", gameId);
        GameDTO game = gameService.getAGame(gameId);
        return ResponseEntity.ok(HttpResponse.builder()
                .timeStamp(LocalDateTime.now().toString())
                .data(Map.of("getGameById", game))
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .build());
    }
}
