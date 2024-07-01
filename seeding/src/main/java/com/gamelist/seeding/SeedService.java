package com.gamelist.seeding;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamelist.seeding.entity.*;
import com.gamelist.seeding.repository.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SeedService {
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
    }

    @Transactional
    public void seedUsersIfEmpty() {
        if (userMongoRepository.count() == 0) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                InputStream inputStream = getClass().getResourceAsStream("/json/users.json");
                List<User> users = objectMapper.readValue(inputStream, new TypeReference<>() {
                });

                users.forEach(user -> {
                    user.setCreatedAt(LocalDateTime.now());
                    user.setUpdatedAt(LocalDateTime.now());
                });

                userMongoRepository.saveAll(users);
            } catch (IOException e) {
                e.printStackTrace();
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
                e.printStackTrace();
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
                e.printStackTrace();
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
                e.printStackTrace();
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
                    game.setId(gameNode.get("id").asLong());
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
                        genres.add(genre);
                    }
                    game.setGenres(new HashSet<>(genres));

                    List<Tag> tags = new ArrayList<>();
                    JsonNode tagsNode = gameNode.get("tags");
                    for (JsonNode tagNode : tagsNode) {
                        Tag tag = tagRepository.findByName(tagNode.asText());
                        if (tag == null) {
                            tag = new Tag();
                            tag.setName(tagNode.asText());
                            tagRepository.save(tag);
                        }
                        tags.add(tag);
                    }
                    game.setTags(new HashSet<>(tags));

                    List<Platform> platforms = new ArrayList<>();
                    JsonNode platformsNode = gameNode.get("platforms");
                    for (JsonNode platformNode : platformsNode) {
                        Platform platform = platformRepository.findByName(platformNode.asText());
                        if (platform == null) {
                            platform = new Platform();
                            platform.setName(platformNode.asText());
                            platformRepository.save(platform);
                        }
                        platforms.add(platform);
                    }
                    game.setPlatforms(new HashSet<>(platforms));

                    game.setCreatedAt(LocalDateTime.now());
                    game.setUpdatedAt(LocalDateTime.now());

                    games.add(game);
                }

                gameRepository.saveAll(games);
            } catch (IOException e) {
                e.printStackTrace();
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
                        e.printStackTrace();
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
                        e.printStackTrace();
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
            List<StatusUpdate> statusUpdates = new ArrayList<>();

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
                        statusUpdates.add(statusUpdate);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            userGameRepository.saveAll(userGames);
            statusUpdateRepository.saveAll(statusUpdates);
        }
    }
}
