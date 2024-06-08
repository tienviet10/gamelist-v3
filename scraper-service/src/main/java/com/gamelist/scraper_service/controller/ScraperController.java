package com.gamelist.scraper_service.controller;

import com.gamelist.scraper_service.entity.*;
import com.gamelist.scraper_service.model.*;
import com.gamelist.scraper_service.service.*;
import lombok.*;
import org.slf4j.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/scraper")
public class ScraperController {
    private static final Logger logger = LoggerFactory.getLogger(ScraperController.class);
    private final ScraperService scraperService;

    @PostMapping("/webhook")
    public ResponseEntity<HttpResponse> addNewUser(@RequestBody ScrapedObject user) {
        logger.info("Registering new user: {}");
        return null;
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
