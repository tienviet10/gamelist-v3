package com.gamelist.social_service.controller;

import com.gamelist.social_service.dto.CommentDTO;
import com.gamelist.social_service.model.CreateCommentRequest;
import com.gamelist.social_service.model.HttpResponse;
import com.gamelist.social_service.projection.CommentView;
import com.gamelist.social_service.service.CommentService;
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
@RequestMapping("/api/v1/comments")
@CrossOrigin(origins = "*")
public class CommentController {
    private static final Logger log = LoggerFactory.getLogger(CommentController.class);
    private final CommentService commentService;

    //    private final ExampleClient exampleClient;
    //    private final ExampleTwoClient exampleTwoClient;

    @PostMapping
    @Transactional
    public ResponseEntity<HttpResponse> createComment(
            @RequestHeader(name = "userId") String userId,
            @RequestHeader(name = "Authorization", required = false) String authorizationHeader,
            @RequestBody CreateCommentRequest createCommentRequest) {
        log.info("createComment called with userId: {}", userId);
        CommentDTO comment = commentService.createComment(
                userId,
                authorizationHeader,
                createCommentRequest.getInteractiveEntityId(),
                createCommentRequest.getText());

        return ResponseEntity.created(URI.create(""))
                .body(HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(Map.of(
                                "comment",
                                comment,
                                "interactiveEntityId",
                                createCommentRequest.getInteractiveEntityId()))
                        .status(HttpStatus.CREATED)
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Comment created successfully")
                        .build());
    }

    @DeleteMapping("/{requestedId}")
    @Transactional
    public ResponseEntity<HttpResponse> deleteComment(
            @RequestHeader(name = "userId") String userId, @PathVariable Long requestedId) {
        log.info("deleteComment called with userId: {}", userId);
        commentService.deleteCommentById(userId, requestedId);
        return ResponseEntity.ok(HttpResponse.builder()
                .timeStamp(LocalDateTime.now().toString())
                .status(HttpStatus.NO_CONTENT)
                .statusCode(HttpStatus.NO_CONTENT.value())
                .message("Comment deleted successfully")
                .build());
    }

    @PutMapping("/{requestedId}")
    @Transactional
    public ResponseEntity<HttpResponse> updateComment(
            @RequestHeader(name = "userId") String userId,
            @PathVariable Long requestedId,
            @RequestBody CreateCommentRequest createCommentRequest) {
        log.info("updateComment called with userId: {}", userId);
        CommentView comment = commentService.updateCommentById(userId, requestedId, createCommentRequest.getText());

        return ResponseEntity.ok(HttpResponse.builder()
                .timeStamp(LocalDateTime.now().toString())
                .data(Map.of("comment", comment))
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .message("Comment updated successfully")
                .build());
    }
}
