package com.gamelist.controller;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.*;

@RestController
public class WebhookController {

    @PostMapping("/webhook")
    public Mono<HttpStatus> handleWebhook(@RequestBody String payload) {
        return Mono.fromRunnable(() -> {
            System.out.println("Received payload: " + payload);
        }).thenReturn(HttpStatus.OK);
    }
}
