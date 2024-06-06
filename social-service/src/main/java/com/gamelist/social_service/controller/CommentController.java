package com.gamelist.social_service.controller;

import com.gamelist.social_service.model.CreateCommentRequest;
import com.gamelist.social_service.model.HttpResponse;
import com.gamelist.social_service.projection.CommentView;
import com.gamelist.social_service.service.CommentService;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
@CrossOrigin(origins = "*")
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    @Transactional
    public ResponseEntity<HttpResponse> createComment(
            @RequestHeader(name = "userId") Long userId, @RequestBody CreateCommentRequest createCommentRequest) {
        CommentView comment = commentService.createComment(
                userId, createCommentRequest.getInteractiveEntityId(), createCommentRequest.getText());

        return ResponseEntity.created(URI.create(""))
                .body(HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(Map.of("comment", comment))
                        .status(HttpStatus.CREATED)
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Comment created successfully")
                        .build());
    }

    @DeleteMapping("/{requestedId}")
    @Transactional
    public ResponseEntity<HttpResponse> deleteComment(
            @RequestHeader(name = "userId") Long userId, @PathVariable Long requestedId) {
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
            @RequestHeader(name = "userId") Long userId,
            @PathVariable Long requestedId,
            @RequestBody CreateCommentRequest createCommentRequest) {
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
