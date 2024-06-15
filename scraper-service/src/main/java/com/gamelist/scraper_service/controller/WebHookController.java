package com.gamelist.scraper_service.controller;

import com.gamelist.scraper_service.model.*;
import com.gamelist.scraper_service.service.*;
import lombok.*;
import org.slf4j.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/scraper")
public class WebHookController {
    private static final Logger logger = LoggerFactory.getLogger(WebHookController.class);
    private final IGDBWebHookService igdbWebHookService;

    @CrossOrigin(origins = "http://localhost:9000")
    @PostMapping("/webhook")
    public ResponseEntity<HttpResponse> onWebHookResponse(@RequestBody IGDBWebHookGameResponse gameResponse, @RequestHeader(value = "X-Secret", required = true) String secret) {
        // TODO: Process request
        // - Validate secret string - ensure this is valid and signed correctly
        // - Strategy pattern - We have to do different things depending on the response: create, delete, update
        //      - We probably won't support delete right now, but I don't want to fully exclude it in the future
        logger.info("Received a webhook response, {}, (Secret: {})", gameResponse, secret);


        // TODO: If secret is invalid:
        //return ResponseEntity.badRequest().build();


        // We must always return a status 200 (OK) to IGDB
        return ResponseEntity.ok().build();
//        return ResponseEntity.created(URI.create(""))
//                .body(HttpResponse.builder()
//                        .timeStamp(LocalDateTime.now().toString())
//                        .data(authService.attemptSignup(user.getEmail(), user.getPassword(), user.getUsername()))
//                        .status(HttpStatus.OK)
//                        .statusCode(HttpStatus.OK.value())
//                        .message("Login successfully")
//                        .build());
    }
}
