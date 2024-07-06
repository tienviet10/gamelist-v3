package com.gamelist.game_service.scraper;

import com.gamelist.game_service.config.*;
import com.gamelist.game_service.repository.*;
import com.gamelist.game_service.scraper.entities.*;
import com.google.gson.*;
import lombok.*;
import org.slf4j.*;
import org.springframework.http.*;
import org.springframework.http.client.*;
import org.springframework.scheduling.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.client.*;

import java.util.*;

@Service
@RequiredArgsConstructor
public class TestScraper {

    private final Logger logger = LoggerFactory.getLogger(TestScraper.class);
    private final GameConfiguration gameConfiguration;
    private final GameRepository gameRepository;

    private final RestClient customClient = RestClient.builder()
            .requestFactory(new HttpComponentsClientHttpRequestFactory())
            .build();
    private final Gson gson = new Gson();

    private int lastId = 0;

//    @Scheduled(fixedRate = 5000)
    public void scrap() {
        logger.info(gameConfiguration.getAccess_token());
        RestClient.ResponseSpec spec = customClient.post()
                .uri("https://api.igdb.com/v4/covers")
                .accept(MediaType.APPLICATION_JSON)
                .header("Client-ID", "")
                .header("Authorization", "Bearer " + gameConfiguration.getAccess_token())
                .body("fields: *;")
                .retrieve();

        String response = spec.body(String.class);
        logger.info(response);
        CoverEntity[] userArray = gson.fromJson(response, CoverEntity[].class);

//        Type personListType = new TypeToken<List<TestParseClass>>() {}.getType();
//        TestParseClass person = gson.fromJson(spec.body(String.class), personListType);

        logger.info(Arrays.toString(userArray));
//                .retrieve();
    }
}
