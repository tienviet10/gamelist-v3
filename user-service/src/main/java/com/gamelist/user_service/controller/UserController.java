package com.gamelist.user_service.controller;

import com.gamelist.user_service.model.HttpResponse;
import com.gamelist.user_service.service.impl.UserServiceImpl;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
@CrossOrigin(origins = "*")
public class UserController {
    private final UserServiceImpl userServiceImpl;

    @GetMapping("/userinfo")
    public ResponseEntity<HttpResponse> getUser(@RequestHeader(name = "userId") Long userId) {

        return ResponseEntity.ok(HttpResponse.builder()
                .timeStamp(LocalDateTime.now().toString())
                .data(userServiceImpl.getUser(userId))
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .message("Retrieved user successfully")
                .build());
    }
}
