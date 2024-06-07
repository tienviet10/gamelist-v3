package com.gamelist.auth_service.controller;

import com.gamelist.auth_service.entity.User;
import com.gamelist.auth_service.model.HttpResponse;
import com.gamelist.auth_service.model.LoginRequest;
import com.gamelist.auth_service.service.impl.AuthServiceImpl;
import java.net.URI;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private final AuthServiceImpl authService;

    @PostMapping("/register")
    public ResponseEntity<HttpResponse> addNewUser(@RequestBody User user) {
        log.info("Registering new user: {}", user.getEmail());
        return ResponseEntity.created(URI.create(""))
                .body(HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(authService.attemptSignup(user.getEmail(), user.getPassword(), user.getUsername()))
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("Login successfully")
                        .build());
    }

    @PostMapping("/login")
    public ResponseEntity<HttpResponse> login(@RequestBody @Validated LoginRequest requests) {
        log.info("Logging in user: {}", requests.getEmail());
        return ResponseEntity.created(URI.create(""))
                .body(HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(authService.attemptLogin(requests.getEmail(), requests.getPassword()))
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("Login successfully")
                        .build());
    }
}
