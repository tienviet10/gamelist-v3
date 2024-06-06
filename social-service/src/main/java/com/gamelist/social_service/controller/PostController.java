package com.gamelist.social_service.controller;

import com.gamelist.social_service.entity.Post;
import com.gamelist.social_service.model.HttpResponse;
import com.gamelist.social_service.projection.PostView;
import com.gamelist.social_service.service.PostService;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
@CrossOrigin(origins = "*")
public class PostController {
    private final PostService postService;

    @GetMapping
    public ResponseEntity<HttpResponse> getAllPostsByUser(@RequestHeader(name = "userId") Long userId) {
        List<PostView> posts = postService.findAllPostsByUserId(userId);

        return ResponseEntity.ok(HttpResponse.builder()
                .timeStamp(LocalDateTime.now().toString())
                .data(Map.of("posts", posts))
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .message("Posts retrieved successfully")
                .build());
    }

    @GetMapping("/all")
    public ResponseEntity<HttpResponse> getAllPosts(@RequestHeader(name = "userId") Long userId) {
        List<PostView> posts = postService.findAllPosts(userId);

        return ResponseEntity.ok(HttpResponse.builder()
                .timeStamp(LocalDateTime.now().toString())
                .data(Map.of("posts", posts))
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .message("Posts retrieved successfully")
                .build());
    }

    @GetMapping("/{requestedId}")
    public ResponseEntity<HttpResponse> findPostById(
            @PathVariable Long requestedId, @RequestHeader(name = "userId") Long userId) {
        PostView post = postService.findPostById(requestedId, userId);

        return ResponseEntity.ok(HttpResponse.builder()
                .timeStamp(LocalDateTime.now().toString())
                .data(Map.of("post", post))
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .message("Post retrieved successfully")
                .build());
    }

    @PostMapping
    public ResponseEntity<HttpResponse> createPost(
            @RequestBody Post post, @RequestHeader(name = "userId") Long userId) {
        PostView createdPost = postService.createPost(post, userId);

        return ResponseEntity.created(URI.create(""))
                .body(HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(Map.of("post", createdPost))
                        .status(HttpStatus.CREATED)
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Post created successfully")
                        .build());
    }

    @PutMapping("/{requestedId}")
    public ResponseEntity<HttpResponse> updatePost(
            @PathVariable Long requestedId, @RequestBody Post post, @RequestHeader(name = "userId") Long userId) {
        PostView updatedPost = postService.updatePostById(requestedId, post, userId);

        return ResponseEntity.ok(HttpResponse.builder()
                .timeStamp(LocalDateTime.now().toString())
                .data(Map.of("post", updatedPost))
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .message("Post updated successfully")
                .build());
    }

    @DeleteMapping("/{requestedId}")
    public ResponseEntity<HttpResponse> deletePostById(
            @PathVariable Long requestedId, @RequestHeader(name = "userId") Long userId) {
        postService.deletePostById(requestedId, userId);

        return ResponseEntity.ok(HttpResponse.builder()
                .timeStamp(LocalDateTime.now().toString())
                .data(Map.of())
                .status(HttpStatus.NO_CONTENT)
                .statusCode(HttpStatus.NO_CONTENT.value())
                .message("Post deleted successfully")
                .build());
    }
}
