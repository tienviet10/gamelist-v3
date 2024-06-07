package com.gamelist.user_service.controller;

import com.gamelist.user_service.model.HttpResponse;
import com.gamelist.user_service.service.impl.UserServiceImpl;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
@CrossOrigin(origins = "*")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserServiceImpl userServiceImpl;

    @GetMapping("/userinfo")
    public ResponseEntity<HttpResponse> getUser(@RequestHeader(name = "userId") Long userId) {
        log.info("getUser called with userId: {}", userId);
        return ResponseEntity.ok(HttpResponse.builder()
                .timeStamp(LocalDateTime.now().toString())
                .data(userServiceImpl.getUser(userId))
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .message("Retrieved user successfully")
                .build());
    }
}
