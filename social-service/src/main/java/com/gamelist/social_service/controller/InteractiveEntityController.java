package com.gamelist.social_service.controller;

import com.gamelist.social_service.model.HttpResponse;
import com.gamelist.social_service.model.PostAndStatusUpdateResponse;
import com.gamelist.social_service.service.InteractiveEntityService;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/interactive-entities")
@CrossOrigin(origins = "*")
public class InteractiveEntityController {
    private final InteractiveEntityService interactiveEntityService;

    @GetMapping("/forum-pageable")
    @Transactional
    public ResponseEntity<HttpResponse> getAllPostAndStatusUpdatePageable(
            @RequestParam(value = "startingId", required = false) Long startingId,
            @RequestParam(value = "limit", required = false, defaultValue = "20") Integer limit) {
        PostAndStatusUpdateResponse postAndStatusUpdateResponse;

        if (startingId == 0) {
            postAndStatusUpdateResponse = interactiveEntityService.getAllPostAndStatusUpdatesFirstPage(limit);
        } else {
            postAndStatusUpdateResponse =
                    interactiveEntityService.getAllPostAndStatusUpdatesByStartingId(startingId, limit);
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
            @RequestHeader(name = "userId") Long userId,
            @RequestParam(value = "startingId", required = false) Long startingId,
            @RequestParam(value = "limit", required = false, defaultValue = "20") Integer limit) {
        PostAndStatusUpdateResponse postAndStatusUpdateResponse;

        if (startingId == 0)
            postAndStatusUpdateResponse =
                    interactiveEntityService.getPostAndStatusUpdateByUserIdFirstPage(userId, limit);
        else
            postAndStatusUpdateResponse =
                    interactiveEntityService.getPostAndStatusUpdateByUserIdAndStartingId(userId, startingId, limit);

        return ResponseEntity.ok(HttpResponse.builder()
                .timeStamp(LocalDateTime.now().toString())
                .data(Map.of("postsAndStatusUpdates", postAndStatusUpdateResponse))
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .message("Posts And StatusUpdates retrieved successfully. ")
                .build());
    }

    @GetMapping("/user-social")
    @Transactional
    public ResponseEntity<HttpResponse> getPostAndStatusUpdateByUserId(@RequestHeader(name = "userId") Long userId) {

        PostAndStatusUpdateResponse postAndStatusUpdateResponse =
                interactiveEntityService.getPostAndStatusUpdateByUserId(userId);

        return ResponseEntity.ok(HttpResponse.builder()
                .timeStamp(LocalDateTime.now().toString())
                .data(Map.of("postsAndStatusUpdates", postAndStatusUpdateResponse))
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .message("Posts And StatusUpdates retrieved successfully. ")
                .build());
    }
}
