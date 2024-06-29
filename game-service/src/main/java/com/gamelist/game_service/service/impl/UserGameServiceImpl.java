package com.gamelist.game_service.service.impl;

import com.gamelist.game_service.clients.user.HttpResponseModel;
import com.gamelist.game_service.clients.user.UserServiceClient;
import com.gamelist.game_service.dto.GameDTO;
import com.gamelist.game_service.dto.UserGamesSummaryDTO;
import com.gamelist.game_service.entity.Game;
import com.gamelist.game_service.entity.GameStatus;
import com.gamelist.game_service.entity.StatusUpdate;
import com.gamelist.game_service.entity.UserGame;
import com.gamelist.game_service.exception.InvalidTokenException;
import com.gamelist.game_service.exception.ResourceNotFoundException;
import com.gamelist.game_service.mapper.GameMapper;
import com.gamelist.game_service.model.EditUserGameRequest;
import com.gamelist.game_service.repository.GameRepository;
import com.gamelist.game_service.repository.LikeRepository;
import com.gamelist.game_service.repository.StatusUpdateRepository;
import com.gamelist.game_service.repository.UserGameRepository;
import com.gamelist.game_service.service.UserGameService;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserGameServiceImpl implements UserGameService {
    //    TODO: Maybe called User Repository from a different container
    //    private final UserRepository userRepository;
    private final UserServiceClient client;
    private final UserGameRepository userGameRepository;
    private final GameRepository gameRepository;
    private final LikeRepository likeRepository;
    private final StatusUpdateRepository statusUpdateRepository;
    private final GameMapper gameMapper;

    //    @Override
    //    public UserGame findUserGameById(Long requestedId, User principal) {
    //        if (principal == null) {
    //            throw new InvalidTokenException("Invalid principal user");
    //        }
    //
    //        Optional<UserGame> userGame = userGameRepository.findByGameIdAndUserId(requestedId, principal.getId());
    //
    //        if (userGame.isPresent()) {
    //            return userGame.get();
    //        }
    //
    //        throw new ResourceNotFoundException("UserGame not found with ID: " + requestedId + " and UserID: " +
    // principal.getId());
    //    }
    //

    @Override
    public UserGame createUserGame(EditUserGameRequest userGame, String userId) {
        if (userId == null) {
            throw new InvalidTokenException("Invalid token");
        }

        UserGame existingUserGame = userGameRepository.findFirstByUserIdAndGameId(userId, userGame.getGameId());
        StatusUpdate statusUpdate = new StatusUpdate();

        if (existingUserGame != null) {
            // If the UserGame already exists, update the existing instance
            if (existingUserGame.getGameStatus() != userGame.getGameStatus()) {
                statusUpdate.setUserGame(existingUserGame);
                statusUpdate.setGameStatus(userGame.getGameStatus());
                statusUpdateRepository.save(statusUpdate);
            }
            existingUserGame.setIsPrivate(userGame.getIsPrivate());
            existingUserGame.setRating(userGame.getRating());
            existingUserGame.setStartDate(userGame.getStartDate());
            existingUserGame.setCompletedDate(userGame.getCompletedDate());
            existingUserGame.setGameStatus(userGame.getGameStatus());
            existingUserGame.setGameNote(userGame.getGameNote());
            userGameRepository.save(existingUserGame);

            return existingUserGame;
        } else {
            // If the UserGame does not exist, create a new instance
            Game game = gameRepository
                    .findById(userGame.getGameId())
                    .orElseThrow(
                            () -> new ResourceNotFoundException("Game not found with ID: " + userGame.getGameId()));

            UserGame newUserGame = UserGame.builder()
                    .game(game)
                    .userId(userId)
                    .gameStatus(userGame.getGameStatus())
                    .isPrivate(userGame.getIsPrivate())
                    .rating(userGame.getRating())
                    .startDate(userGame.getStartDate())
                    .completedDate(userGame.getCompletedDate())
                    .gameNote(userGame.getGameNote())
                    .build();

            statusUpdate.setUserGame(newUserGame);
            statusUpdate.setGameStatus(newUserGame.getGameStatus());
            userGameRepository.save(newUserGame);
            statusUpdateRepository.save(statusUpdate);
            return newUserGame;
        }
    }

    @Override
    public UserGame updateUserGameById(EditUserGameRequest userGame, String userId) {
        if (userId == null) throw new InvalidTokenException("Invalid token");

        Optional<UserGame> userGameOptional = userGameRepository.findByGameIdAndUserId(userGame.getGameId(), userId);

        if (userGameOptional.isPresent()) {
            UserGame responseData = userGameOptional.get();

            if (userGame.getGameStatus() != responseData.getGameStatus()) {
                StatusUpdate statusUpdate = new StatusUpdate();
                statusUpdate.setUserGame(responseData);
                statusUpdate.setGameStatus(userGame.getGameStatus());
                statusUpdateRepository.save(statusUpdate);
            }

            responseData.setGameStatus(userGame.getGameStatus());
            responseData.setGameNote(userGame.getGameNote());
            responseData.setIsPrivate(userGame.getIsPrivate());
            responseData.setRating(userGame.getRating());
            responseData.setCompletedDate(userGame.getCompletedDate());
            responseData.setStartDate(userGame.getStartDate());

            return userGameRepository.save(responseData);
        }

        throw new ResourceNotFoundException(
                "UserGame not found with ID: " + userGame.getGameId() + " and UserID: " + userId);
    }

    //    @Override
    //    public UserGame deleteUserGameById(Long requestedId, User principal) {
    //        if (principal == null) throw new InvalidTokenException("Invalid token");
    //
    //        Optional<UserGame> userGameOptional = userGameRepository.findById(requestedId);
    //
    //        if (userGameOptional.isPresent()) {
    //            UserGame responseData = userGameOptional.get();
    //            User user = responseData.getUser();
    //
    //            if (principal.getId().equals(user.getId())) {
    //                return resetUserGameAndStatusUpdate(responseData);
    //            }
    //            throw new InvalidAuthorizationException("Invalid authorization");
    //        }
    //
    //        throw new ResourceNotFoundException("UserGame not found with ID: " + requestedId);
    //    }
    //
    @Override
    public Set<UserGame> findAllUserGamesByUserId(String userId) {
        Optional<Set<UserGame>> optionalUserGames = userGameRepository.findAllByUserId(userId);

        if (optionalUserGames.isPresent()) {
            return optionalUserGames.get();
        }

        throw new ResourceNotFoundException("UserGame not found with ID: " + userId);
    }

    @Override
    public UserGamesSummaryDTO findAllUserGamesByUserIdByStatus(String userId, String authorizationHeader) {
        List<Game> playingGames = gameRepository.findGamesByUserIdAndStatus(userId, GameStatus.Playing);
        List<GameDTO> playingGameDTOs = gameMapper.gamesToGameDTOs(playingGames);
        List<Game> completedGames = gameRepository.findGamesByUserIdAndStatus(userId, GameStatus.Completed);
        List<GameDTO> completedGameDTOs = gameMapper.gamesToGameDTOs(completedGames);
        List<Game> pausedGames = gameRepository.findGamesByUserIdAndStatus(userId, GameStatus.Paused);
        List<GameDTO> pausedGameDTOs = gameMapper.gamesToGameDTOs(pausedGames);
        List<Game> planningGames = gameRepository.findGamesByUserIdAndStatus(userId, GameStatus.Planning);
        List<GameDTO> planningGameDTOs = gameMapper.gamesToGameDTOs(planningGames);
        List<Game> dropGames = gameRepository.findGamesByUserIdAndStatus(userId, GameStatus.Dropped);
        List<GameDTO> dropGameDTOs = gameMapper.gamesToGameDTOs(dropGames);
        List<Game> justAdded = gameRepository.findGamesByUserIdAndStatus(userId, GameStatus.JustAdded);
        List<GameDTO> justAddedGameDTOs = gameMapper.gamesToGameDTOs(justAdded);

        applyGameAddedAndLikeToGameDTOs(playingGameDTOs, userId);
        applyGameAddedAndLikeToGameDTOs(completedGameDTOs, userId);
        applyGameAddedAndLikeToGameDTOs(pausedGameDTOs, userId);
        applyGameAddedAndLikeToGameDTOs(planningGameDTOs, userId);
        applyGameAddedAndLikeToGameDTOs(dropGameDTOs, userId);
        applyGameAddedAndLikeToGameDTOs(justAddedGameDTOs, userId);

        UserGamesSummaryDTO userGamesSummary = new UserGamesSummaryDTO();
        userGamesSummary.setPlaying(playingGameDTOs);
        userGamesSummary.setPlayingCount(playingGameDTOs.size());
        userGamesSummary.setCompleted(completedGameDTOs);
        userGamesSummary.setCompletedCount(completedGameDTOs.size());
        userGamesSummary.setPaused(pausedGameDTOs);
        userGamesSummary.setPausedCount(pausedGameDTOs.size());
        userGamesSummary.setPlanning(planningGameDTOs);
        userGamesSummary.setPlanningCount(planningGameDTOs.size());
        userGamesSummary.setDropped(dropGameDTOs);
        userGamesSummary.setDroppedCount(dropGameDTOs.size());
        userGamesSummary.setJustAdded(justAddedGameDTOs);
        userGamesSummary.setJustAddedCount(justAddedGameDTOs.size());

        int totalCount = playingGameDTOs.size()
                + completedGameDTOs.size()
                + pausedGameDTOs.size()
                + planningGameDTOs.size()
                + dropGameDTOs.size()
                + justAddedGameDTOs.size();
        userGamesSummary.setTotalCount(totalCount);

        String listsOrder = "playing,completed,paused,planning,dropped,justAdded";
        Optional<HttpResponseModel> result = client.getUserInfoById(userId, authorizationHeader);
        if (result.isPresent()) {
            listsOrder = result.get().data().get("listsOrder").toString();
        }
        userGamesSummary.setListsOrder(listsOrder);

        return userGamesSummary;
    }

    @Override
    public UserGame findUserGameByGameId(Long gameId, String userId) {
        Optional<UserGame> userGameOptional = userGameRepository.findByGameIdAndUserId(gameId, userId);

        if (userGameOptional.isPresent()) {
            UserGame responseData = userGameOptional.get();

            if (responseData.getGameStatus() == GameStatus.JustAdded) {
                responseData.setGameStatus(null);
            }
            return userGameOptional.get();
        }

        return UserGame.builder()
                .gameStatus(GameStatus.Inactive)
                .gameNote("")
                .isPrivate(false)
                .gameNote("")
                .game(gameRepository
                        .findById(gameId)
                        .orElseThrow(() -> new ResourceNotFoundException("Game can not find by ID: " + gameId)))
                .rating(null)
                .userId(userId)
                .build();
    }

    @Override
    public UserGame deleteUserGameByGameId(Long gameId, String userId) {
        if (userId == null) throw new InvalidTokenException("Invalid token");

        Optional<UserGame> userGameOptional = userGameRepository.findByGameIdAndUserId(gameId, userId);

        if (userGameOptional.isPresent()) {
            UserGame responseData = userGameOptional.get();
            return resetUserGameAndStatusUpdate(responseData);
        }
        throw new ResourceNotFoundException("UserGame not found with Game ID: " + gameId + " and User ID: " + userId);
    }

    private UserGame resetUserGameAndStatusUpdate(UserGame userGame) {
        userGame.setGameStatus(GameStatus.Inactive);
        userGame.setGameNote(null);
        userGame.setRating(null);

        userGame.setCompletedDate(null);
        userGame.setStartDate(null);
        userGame.setIsPrivate(false);

        StatusUpdate statusUpdate = new StatusUpdate();
        statusUpdate.setUserGame(userGame);
        statusUpdate.setGameStatus(userGame.getGameStatus());
        statusUpdateRepository.save(statusUpdate);
        return userGameRepository.save(userGame);
    }

    private void applyGameAddedAndLikeToGameDTOs(List<GameDTO> gameDTOs, String userId) {
        for (GameDTO gameDTO : gameDTOs) {
            if (userId == null) {
                gameDTO.setGameAdded(false);
                gameDTO.setGameLiked(false);
                continue;
            }

            gameDTO.setGameAdded(
                    userGameRepository.existsByGameIdAndUserIdAndGameStatusNotInactive(gameDTO.getId(), userId));
            gameDTO.setGameLiked(likeRepository.existsByUserIdAndInteractiveEntityId(userId, gameDTO.getId()));
        }
    }
}
