package com.gamelist.social_service.controller;

import com.gamelist.social_service.model.HttpResponse;
import com.gamelist.social_service.model.PostAndStatusUpdateResponseV2;
import com.gamelist.social_service.service.InteractiveEntityService;
import java.io.IOException;
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
@RequestMapping("/api/v1/interactive-entities")
@CrossOrigin(origins = "*")
public class InteractiveEntityController {
    private static final Logger log = LoggerFactory.getLogger(InteractiveEntityController.class);
    private final InteractiveEntityService interactiveEntityService;

    @GetMapping("/forum-pageable")
    @Transactional
    public ResponseEntity<HttpResponse> getAllPostAndStatusUpdatePageable(
            @RequestHeader(name = "Authorization") String authorizationHeader,
            @RequestParam(value = "startingId", required = false, defaultValue = "0") Long startingId,
            @RequestParam(value = "limit", required = false, defaultValue = "20") Integer limit)
            throws IOException {
        log.info("getAllPostAndStatusUpdatePageable called with startingId: {}", startingId);
        PostAndStatusUpdateResponseV2 postAndStatusUpdateResponse;

        if (startingId == 0) {
            postAndStatusUpdateResponse =
                    interactiveEntityService.getAllPostAndStatusUpdatesFirstPage(authorizationHeader, limit);
        } else {
            postAndStatusUpdateResponse = interactiveEntityService.getAllPostAndStatusUpdatesByStartingId(
                    authorizationHeader, startingId, limit);
        }

        return ResponseEntity.ok(HttpResponse.builder()
                .timeStamp(LocalDateTime.now().toString())
                .data(Map.of("postsAndStatusUpdates", postAndStatusUpdateResponse))
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .message("Posts And StatusUpdates retrieved successfully. ")
                .build());
    }

    @GetMapping("/user-social/pageable")
    @Transactional
    public ResponseEntity<HttpResponse> getPostAndStatusUpdateByUserIdPageable(
            @RequestHeader(name = "Authorization") String authorizationHeader,
            @RequestHeader(name = "userId") String userId,
            @RequestParam(value = "startingId", required = false, defaultValue = "0") Long startingId,
            @RequestParam(value = "limit", required = false, defaultValue = "20") Integer limit)
            throws IOException {
        log.info("getPostAndStatusUpdateByUserIdPageable called with userId: {}", userId);
        PostAndStatusUpdateResponseV2 postAndStatusUpdateResponse;

        if (startingId == 0)
            postAndStatusUpdateResponse = interactiveEntityService.getPostAndStatusUpdateByUserIdFirstPage(
                    authorizationHeader, userId, limit);
        else
            postAndStatusUpdateResponse = interactiveEntityService.getPostAndStatusUpdateByUserIdAndStartingId(
                    authorizationHeader, userId, startingId, limit);

        return ResponseEntity.ok(HttpResponse.builder()
                .timeStamp(LocalDateTime.now().toString())
                .data(Map.of("postsAndStatusUpdates", postAndStatusUpdateResponse))
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .message("Posts And StatusUpdates retrieved successfully. ")
                .build());
    }
}
