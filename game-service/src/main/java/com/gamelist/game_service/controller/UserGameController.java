package com.gamelist.game_service.controller;

import com.gamelist.game_service.dto.UserGamesSummaryDTO;
import com.gamelist.game_service.entity.UserGame;
import com.gamelist.game_service.exception.InternalServerErrorException;
import com.gamelist.game_service.model.EditUserGameRequest;
import com.gamelist.game_service.model.HttpResponse;
import com.gamelist.game_service.service.UserGameService;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/usergames")
@CrossOrigin(origins = "*")
public class UserGameController {
    private static final Logger log = LoggerFactory.getLogger(UserGameController.class);
    private final UserGameService userGameService;

    @GetMapping
    public ResponseEntity<HttpResponse> getAllUserGameByUserId(@RequestHeader(name = "userId") String userId) {
        log.info("getAllUserGameByUserId called with userId: {}", userId);
        Set<UserGame> userGames = userGameService.findAllUserGamesByUserId(userId);

        return ResponseEntity.ok(HttpResponse.builder()
                .timeStamp(LocalDateTime.now().toString())
                .data(Map.of("userGames", userGames))
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .message("UserGames retrieved successfully")
                .build());
    }

    @GetMapping("/status")
    public ResponseEntity<HttpResponse> getAllUserGameByUserIdByStatus(
            @RequestHeader(name = "userId") String userId,
            @RequestHeader(name = "Authorization", required = false) String authorizationHeader) {
        log.info("getAllUserGameByUserIdByStatus called with userId: {}", userId);
        UserGamesSummaryDTO userGames = userGameService.findAllUserGamesByUserIdByStatus(userId, authorizationHeader);

        return ResponseEntity.ok(HttpResponse.builder()
                .timeStamp(LocalDateTime.now().toString())
                .data(Map.of("userGamesByStatus", userGames))
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .message("UserGames retrieved successfully")
                .build());
    }

    @GetMapping("/{requestedId}")
    public ResponseEntity<HttpResponse> findUserGameByGameId(
            @PathVariable("requestedId") Long requestedId, @RequestHeader(name = "userId") String userId) {
        log.info("findUserGameByGameId called with gameId: {}", requestedId);
        UserGame userGame = userGameService.findUserGameByGameId(requestedId, userId);

        return ResponseEntity.ok(HttpResponse.builder()
                .timeStamp(LocalDateTime.now().toString())
                .data(Map.of("userGame", userGame))
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .message("UserGame by Game ID found")
                .build());
    }

    @PostMapping
    public ResponseEntity<HttpResponse> createUserGame(
            @RequestBody EditUserGameRequest userGame, @RequestHeader(name = "userId") String userId) {
        log.info("createUserGame called with userId: {}", userId);
        UserGame createdUserGame = userGameService.createUserGame(userGame, userId);

        if (createdUserGame != null) {
            return ResponseEntity.created(URI.create(""))
                    .body(HttpResponse.builder()
                            .timeStamp(LocalDateTime.now().toString())
                            .data(Map.of("userGame", createdUserGame))
                            .status(HttpStatus.CREATED)
                            .statusCode(HttpStatus.CREATED.value())
                            .message("UserGame created")
                            .build());
        } else {
            throw new InternalServerErrorException("Error creating UserGame");
        }
    }

    @PutMapping
    public ResponseEntity<HttpResponse> updateUserGame(
            @RequestBody EditUserGameRequest userGame, @RequestHeader(name = "userId") String userId) {
        log.info("updateUserGame called with userId: {}", userId);
        UserGame updatedUserGame = userGameService.updateUserGameById(userGame, userId);

        return ResponseEntity.created(URI.create(""))
                .body(HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(Map.of("userGame", updatedUserGame))
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("UserGame updated")
                        .build());
    }

    @DeleteMapping("/{requestedId}")
    public ResponseEntity<HttpResponse> deleteUserGameByGameId(
            @PathVariable("requestedId") Long requestedId, @RequestHeader(name = "userId") String userId) {
        log.info("deleteUserGameByGameId called with userId: {}", userId);
        UserGame deletedUserGame = userGameService.deleteUserGameByGameId(requestedId, userId);

        return ResponseEntity.created(URI.create(""))
                .body(HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(Map.of("userGame", deletedUserGame))
                        .status(HttpStatus.NO_CONTENT)
                        .statusCode(HttpStatus.NO_CONTENT.value())
                        .message("UserGame deleted")
                        .build());
    }
}
