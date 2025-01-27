package com.gamelist.social_service.controller;

import com.gamelist.social_service.dto.LikeEntityDTO;
import com.gamelist.social_service.entity.CreateLikeEntityRequest;
import com.gamelist.social_service.model.HttpResponse;
import com.gamelist.social_service.service.LikeService;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/likes")
@CrossOrigin(origins = "*")
public class LikeController {
    private static final Logger log = LoggerFactory.getLogger(LikeController.class);
    private final LikeService likeService;

    @PostMapping
    @Transactional
    public ResponseEntity<HttpResponse> createLike(
            @RequestHeader(name = "userId") String userId,
            @RequestHeader(name = "Authorization", required = false) String authorizationHeader,
            @RequestBody CreateLikeEntityRequest createLikeEntityRequest) {
        log.info("createLike called with userId: {}", userId);
        LikeEntityDTO like =
                likeService.createLike(userId, authorizationHeader, createLikeEntityRequest.getInteractiveEntityId());

        return ResponseEntity.created(URI.create(""))
                .body(HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(Map.of("like", like))
                        .status(HttpStatus.CREATED)
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Like created successfully")
                        .build());
    }

    @DeleteMapping("/{requestedId}")
    @Transactional
    public ResponseEntity<HttpResponse> deleteLike(
            @RequestHeader(name = "userId") String userId, @PathVariable Long requestedId) {
        log.info("deleteLike called with userId: {}", userId);
        likeService.deleteLikeById(userId, requestedId);

        return ResponseEntity.ok(HttpResponse.builder()
                .timeStamp(LocalDateTime.now().toString())
                .status(HttpStatus.NO_CONTENT)
                .statusCode(HttpStatus.NO_CONTENT.value())
                .message("Like deleted successfully")
                .build());
    }
}
