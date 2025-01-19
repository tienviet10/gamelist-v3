package com.gamelist.seeding;

import com.fasterxml.jackson.core.type.*;
import com.fasterxml.jackson.databind.*;
import com.gamelist.seeding.elastic.modal.*;
import com.gamelist.seeding.elastic.repository.*;
import com.gamelist.seeding.entity.*;
import com.gamelist.seeding.repository.*;
import jakarta.annotation.*;
import lombok.*;
import org.slf4j.*;
import org.springframework.data.mongodb.core.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.io.*;
import java.time.*;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SeedService {

    private final Logger logger = LoggerFactory.getLogger(SeedService.class);
    private final MongoTemplate mongoTemplate;
    private final UserMongoRepository userMongoRepository;
    private final PostRepository postRepository;
    private final GameRepository gameRepository;
    private final GenreRepository genreRepository;
    private final TagRepository tagRepository;
    private final PlatformRepository platformRepository;
    private final UserGameRepository userGameRepository;
    private final GameJournalRepository gameJournalRepository;
    private final StatusUpdateRepository statusUpdateRepository;

    private final ElasticUserRepository elasticUserRepository;

    @PostConstruct
    @Transactional
    public void seedDatabase() {
        seedUsersIfEmpty();
        seedPlatformsIfEmpty();
        seedGenresIfEmpty();
        seedTagsIfEmpty();
        seedGamesIfEmpty();
        seedUserGamesIfEmpty();
        seedPostsIfEmpty();
        seedGameJournalsIfEmpty();

        logger.info("Successfully seeded database");

        // Kill the server when were done seeding - it doesn't need to stay active
        System.exit(1);
    }

    @Transactional
    public void seedUsersIfEmpty() {
        if (userMongoRepository.count() == 0) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                InputStream inputStream = getClass().getResourceAsStream("/json/users.json");
                List<User> users = objectMapper.readValue(inputStream, new TypeReference<>() {
                });

                users.forEach(user -> {
                    user.setCreatedAt(LocalDateTime.now());
                    user.setUpdatedAt(LocalDateTime.now());
                });

                List<User> dbUsers = userMongoRepository.saveAll(users);

                List<ElasticUser> elasticUsers = dbUsers.stream().map(originalUser -> {
                    ElasticUser elasticUser = new ElasticUser();
                    elasticUser.setId(originalUser.getId());
                    elasticUser.setUsername(originalUser.getUsername());
                    elasticUser.setEmail(originalUser.getEmail());
                    return elasticUser;
                }).toList();

                elasticUserRepository.saveAll(elasticUsers);
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
    }

    @Transactional
    public void seedPlatformsIfEmpty() {
        if (platformRepository.count() == 0) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                InputStream inputStream = getClass().getResourceAsStream("/json/platforms.json");
                List<Platform> platforms = objectMapper.readValue(inputStream, new TypeReference<>() {
                });

                platformRepository.saveAll(platforms);
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
    }

    @Transactional
    public void seedGenresIfEmpty() {
        if (genreRepository.count() == 0) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                InputStream inputStream = getClass().getResourceAsStream("/json/genres.json");
                List<Genre> genres = objectMapper.readValue(inputStream, new TypeReference<>() {
                });
                genreRepository.saveAll(genres);
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
    }

    @Transactional
    public void seedTagsIfEmpty() {
        if (tagRepository.count() == 0) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                InputStream inputStream = getClass().getResourceAsStream("/json/tags.json");
                List<Tag> tags = objectMapper.readValue(inputStream, new TypeReference<>() {
                });
                tagRepository.saveAll(tags);
            } catch (IOException e) {
                logger.error(e.getMessage());
            }

        }
    }

    @Transactional
    public void seedGamesIfEmpty() {
        if (gameRepository.count() == 0) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {

                InputStream inputStream = getClass().getResourceAsStream("/json/games.json");
                List<JsonNode> gameNodes = objectMapper.readValue(inputStream, new TypeReference<>() {
                });

                List<Game> games = new ArrayList<>();

                for (JsonNode gameNode : gameNodes) {
                    Game game = new Game();
                    game.setName(gameNode.get("name").asText());
                    game.setDescription(gameNode.get("summary").asText());
                    game.setImageURL("https:" + gameNode.get("cover").asText());
                    game.setReleaseDate(LocalDateTime.ofInstant(Instant.ofEpochSecond(gameNode.get("first_release_date").asLong()), ZoneId.systemDefault()));
                    game.setAvgScore(Math.round(gameNode.get("total_rating").asDouble() * 10.0) / 10.0);
                    game.setTotalRating(gameNode.get("total_rating_count").asInt());
                    game.setBannerURL("https:" + gameNode.get("screenshots").get(0).asText());

                    List<Genre> genres = new ArrayList<>();
                    JsonNode genresNode = gameNode.get("genres");
                    for (JsonNode genreNode : genresNode) {
                        Genre genre = genreRepository.findByName(genreNode.asText());
                        logger.info("Genre {}", genre.getId());
                        genres.add(genre);
                    }
                    game.setGenres(new HashSet<>(genres));

                    List<Tag> tags = new ArrayList<>();
                    JsonNode tagsNode = gameNode.get("tags");
                    for (JsonNode tagNode : tagsNode) {
                        Tag tag = tagRepository.findByName(tagNode.asText());
                        tags.add(tag);
                    }
                    game.setTags(new HashSet<>(tags));

                    List<Platform> platforms = new ArrayList<>();
                    JsonNode platformsNode = gameNode.get("platforms");
                    for (JsonNode platformNode : platformsNode) {
                        Platform platform = platformRepository.findByName(platformNode.asText());
                        platforms.add(platform);
                    }

                    game.setPlatforms(new HashSet<>(platforms));

                    game.setCreatedAt(LocalDateTime.now());
                    game.setUpdatedAt(LocalDateTime.now());


                    games.add(game);

                }


                List<Game> returnedGames = gameRepository.saveAll(games);
                logger.info("Saved {} Games", returnedGames.size());
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
    }

    @Transactional
    public void seedGameJournalsIfEmpty() {
        if (gameJournalRepository.count() == 0) {
            List<User> users = userMongoRepository.findAll();

            List<GameJournal> gameJournals = new ArrayList<>();

            for (User user : users) {
                for (int j = 1; j <= 5; j++) {
                    try {
                        GameJournal gameJournal = new GameJournal();
                        gameJournal.setContent("This is the body of game journal " + j + " by user " + user.getId() + ".");
                        gameJournal.setUserId(user.getId());

                        gameJournals.add(gameJournal);
                    } catch (Exception e) {
                        logger.error(e.getMessage());
                    }
                }
            }

            gameJournalRepository.saveAll(gameJournals);
        }
    }

    @Transactional
    public void seedPostsIfEmpty() {
        if (postRepository.count() == 0) {
            List<User> users = userMongoRepository.findAll();
            List<Post> posts = new ArrayList<>();

            for (User user : users) {
                for (int j = 1; j <= 100; j++) {
                    try {
                        Post post = new Post();
                        post.setText("This is a post " + j + " by user " + user.getId() + ".");
                        post.setUserId(user.getId());

                        posts.add(post);
                    } catch (Exception e) {
                        logger.error(e.getMessage());
                    }
                }
            }

            postRepository.saveAll(posts);
        }
    }

    @Transactional
    public void seedUserGamesIfEmpty() {
        if (userGameRepository.count() == 0) {
            List<User> users = userMongoRepository.findAll();

            List<UserGame> userGames = new ArrayList<>();

            for (User user : users) {
                for (int j = 1; j <= 75; j++) {
                    try {
                        UserGame userGame = new UserGame();
                        userGame.setCreatedAt(LocalDateTime.now());
                        userGame.setUpdatedAt(LocalDateTime.now());
                        userGame.setGameStatus(GameStatus.values()[j % 3]);
                        userGame.setIsPrivate(false);
                        userGame.setUserId(user.getId());
                        userGame.setGame(gameRepository.findAllGamesOrderedById().get(j - 1));
                        userGame.setRating(5);
                        userGame.setGameNote("This is a game review for game " + j + " by user " + user.getId() + ".");
                        userGames.add(userGame);

                        StatusUpdate statusUpdate = new StatusUpdate();
                        statusUpdate.setUserGame(userGame);
                        statusUpdate.setGameStatus(userGame.getGameStatus());
                        statusUpdateRepository.save(statusUpdate);

                    } catch (Exception e) {
                        logger.error(e.getMessage());
                    }
                }
            }

            userGameRepository.saveAll(userGames);

        }
    }
}
