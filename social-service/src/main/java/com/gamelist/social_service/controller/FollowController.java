package com.gamelist.social_service.controller;

import com.gamelist.social_service.model.HttpResponse;
import com.gamelist.social_service.projection.FollowView;
import com.gamelist.social_service.projection.UserBasicView;
import com.gamelist.social_service.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/follows")
@CrossOrigin(origins = "*")
public class FollowController {
    private static final Logger log = LoggerFactory.getLogger(FollowController.class);
    private final FollowService followService;

    @GetMapping
    @Transactional
    public ResponseEntity<HttpResponse> getAllFollows(@RequestHeader(name = "userId") Long userId) {
        log.info("getAllFollows called with userId: {}", userId);
        FollowView userFollows = followService.getAllFollows(userId);

        return ResponseEntity.ok(HttpResponse.builder()
                .timeStamp(LocalDateTime.now().toString())
                .data(Map.of("user", userFollows))
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .message("Follows retrieved successfully")
                .build());
    }

    @PostMapping("/{userId}")
    @Transactional
    public ResponseEntity<HttpResponse> createFollow(
            @RequestHeader(name = "userId") Long tokenUserId, @PathVariable Long userId) {
        log.info("createFollow called with userId: {}", userId);
        UserBasicView userToFollow = followService.createFollow(tokenUserId, userId);

        return ResponseEntity.created(URI.create(""))
                .body(HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(Map.of("user", userToFollow))
                        .status(HttpStatus.CREATED)
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Follow created successfully")
                        .build());
    }

    @DeleteMapping("/{requestedId}")
    @Transactional
    public ResponseEntity<HttpResponse> removeFollow(
            @RequestHeader(name = "userId") Long userId, @PathVariable Long requestedId) {
        log.info("removeFollow called with userId: {}", userId);
        UserBasicView userToUnfollow = followService.removeFollow(userId, requestedId);

        return ResponseEntity.ok(HttpResponse.builder()
                .timeStamp(LocalDateTime.now().toString())
                .data(Map.of("user", userToUnfollow))
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .message("Follow removed successfully")
                .build());
    }

    @DeleteMapping("/followers/{requestedId}")
    @Transactional
    public ResponseEntity<HttpResponse> removeFollower(
            @RequestHeader(name = "userId") Long userId, @PathVariable Long requestedId) {
        log.info("removeFollower called with userId: {}", userId);
        UserBasicView userToUnfollow = followService.removeFollower(userId, requestedId);

        return ResponseEntity.ok(HttpResponse.builder()
                .timeStamp(LocalDateTime.now().toString())
                .data(Map.of("user", userToUnfollow))
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .message("Follower removed successfully")
                .build());
    }
}
