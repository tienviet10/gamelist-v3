package com.gamelist.game_service.scraper;

import com.gamelist.game_service.config.*;
import com.gamelist.game_service.entity.*;
import com.gamelist.game_service.enums.*;
import com.gamelist.game_service.repository.*;
import com.gamelist.game_service.scraper.adapter.*;
import com.google.gson.*;
import lombok.*;
import org.slf4j.*;
import org.springframework.http.*;
import org.springframework.http.client.*;
import org.springframework.scheduling.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.client.*;

import java.time.*;
import java.util.*;

@Service
@RequiredArgsConstructor
public class TestScraper {

    private final Logger logger = LoggerFactory.getLogger(TestScraper.class);
    private final GameConfiguration gameConfiguration;
//    private final GameRepository gameRepository;
    private final GenreRepository genreRepository;
    private final TagRepository tagRepository;
    private final PlatformRepository platformRepository;
    private final PlatformLogoRepository platformLogoRepository;
    private final CompanyLogoRepository companyLogoRepository;
    private final RegionRepository regionRepository;
    private final ReleaseDateStatusRepository releaseDateStatusRepository;
    private final CompanyWebsiteRepository companyWebsiteRepository;
    private final GameEngineLogoRepository gameEngineLogoRepository;

    private final RestClient customClient = RestClient.builder()
            .requestFactory(new HttpComponentsClientHttpRequestFactory())
            .build();
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
            .registerTypeAdapter(WebsiteType.class, new WebsiteTypeAdapter())
            .registerTypeAdapter(AgeRatingContentDescriptionType.class, new AgeRatingContentDescriptionsTypeAdapter())
            .create();

    private Long lastId = 0L;

//    @Scheduled(initialDelay = 1000, fixedDelay=Long.MAX_VALUE)
//    @Scheduled(fixedRate = 3000)
    public void scrap() {
        logger.info(gameConfiguration.getAccess_token());

        StringBuilder queryString = new StringBuilder();
        queryString.append("fields: *; limit 500; sort id asc;");
        if(lastId > 0) {
            queryString.append("where id > ").append(lastId).append(";");
        }

        RestClient.ResponseSpec spec = customClient.post()
                .uri("https://api.igdb.com/v4/game_engine_logos")
                .accept(MediaType.APPLICATION_JSON)
                .header("Client-ID", "")
                .header("Authorization", "")
                .body(queryString.toString())
                .retrieve();

        String response = spec.body(String.class);
//        logger.info(response);
        GameEngineLogo[] result = gson.fromJson(response, GameEngineLogo[].class);
//        logger.info("result {}", result);
//
//        releaseDateStatusRepository.saveAll(Arrays.asList(result));

//        logger.info("done");

        if(result.length > 0) {
            List<GameEngineLogo> companies = Arrays.asList(result);
            lastId = companies.getLast().getId();
//            companies.forEach(companyWebsite -> {
//                try {
//                    companyWebsiteRepository.save(companyWebsite);
//                } catch (Exception e) {
//                    // Log the exception and continue with the next companyWebsite
//                    System.err.println("Error saving companyWebsite: " + companyWebsite.getId() + ", error: " + e.getMessage());
//                }
//            });

//            logger.info("still more: {}", lastId);
            gameEngineLogoRepository.saveAll(companies);
        } else {
            logger.info("done");
        }
    }

    private void doSingle(String response) {
        Genre[] result = gson.fromJson(response, Genre[].class);
//        platformRepository.saveAll(Arrays.stream(result).toList());
//        tagRepository.saveAll(Arrays.stream(result).toList());
//        genreRepository.saveAll(Arrays.stream(result).toList());

        logger.info("done");
    }
}
