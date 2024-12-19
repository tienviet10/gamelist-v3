package com.gamelist.game_service.service.impl;

import com.gamelist.game.UserCategoryList;
import com.gamelist.game_service.clients.user.UserServiceClient;
import com.gamelist.game_service.dto.UserGamesDTO;
import com.gamelist.game_service.dto.UserGamesSummaryDTO;
import com.gamelist.game_service.entity.Game;
import com.gamelist.game_service.entity.GameStatus;
import com.gamelist.game_service.entity.StatusUpdate;
import com.gamelist.game_service.entity.UserGame;
import com.gamelist.game_service.exception.InvalidTokenException;
import com.gamelist.game_service.exception.ResourceNotFoundException;
import com.gamelist.game_service.gRPCService.UserGRPCServiceClient;
import com.gamelist.game_service.mapper.UserGameMapper;
import com.gamelist.game_service.model.EditUserGameRequest;
import com.gamelist.game_service.projection.UserGameProjection;
import com.gamelist.game_service.repository.GameRepository;
import com.gamelist.game_service.repository.StatusUpdateRepository;
import com.gamelist.game_service.repository.UserGameRepository;
import com.gamelist.game_service.service.UserGameService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserGameServiceImpl implements UserGameService {
    private final UserServiceClient client;
    private final UserGameRepository userGameRepository;
    private final GameRepository gameRepository;
    private final StatusUpdateRepository statusUpdateRepository;
    private final UserGameMapper userGameMapper;
    private final UserGRPCServiceClient userGRPCServiceClient;

    @Override
    public UserGame createUserGame(EditUserGameRequest userGame, String userId) {
        if (userId == null) {
            throw new InvalidTokenException("Invalid token");
        }

        UserGame existingUserGame = userGameRepository.findFirstByUserIdAndGameId(userId, userGame.getGameId());
        StatusUpdate statusUpdate = new StatusUpdate();

        if (existingUserGame != null) {
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
        List<UserGameProjection> userGames = userGameRepository.findUserGamesByUserId(userId);
        List<UserGamesDTO> gameDTOs = userGameMapper.userGameProjectionsToGameDTOs(userGames);
        List<UserGamesDTO> playingGameDTOs = new ArrayList<>();
        List<UserGamesDTO> completedGameDTOs = new ArrayList<>();
        List<UserGamesDTO> pausedGameDTOs = new ArrayList<>();
        List<UserGamesDTO> planningGameDTOs = new ArrayList<>();
        List<UserGamesDTO> droppedGameDTOs = new ArrayList<>();
        List<UserGamesDTO> justAddedGameDTOs = new ArrayList<>();

        for (UserGamesDTO gameDTO : gameDTOs) {
            switch (gameDTO.getGameStatus()) {
                case Playing -> playingGameDTOs.add(gameDTO);
                case Completed -> completedGameDTOs.add(gameDTO);
                case Paused -> pausedGameDTOs.add(gameDTO);
                case Planning -> planningGameDTOs.add(gameDTO);
                case Dropped -> droppedGameDTOs.add(gameDTO);
                case JustAdded -> justAddedGameDTOs.add(gameDTO);
                default -> {}
            }
        }
        UserGamesSummaryDTO userGamesSummary = new UserGamesSummaryDTO();
        userGamesSummary.setPlaying(playingGameDTOs);
        userGamesSummary.setPlayingCount(playingGameDTOs.size());
        userGamesSummary.setCompleted(completedGameDTOs);
        userGamesSummary.setCompletedCount(completedGameDTOs.size());
        userGamesSummary.setPaused(pausedGameDTOs);
        userGamesSummary.setPausedCount(pausedGameDTOs.size());
        userGamesSummary.setPlanning(planningGameDTOs);
        userGamesSummary.setPlanningCount(planningGameDTOs.size());
        userGamesSummary.setDropped(droppedGameDTOs);
        userGamesSummary.setDroppedCount(droppedGameDTOs.size());
        userGamesSummary.setJustAdded(justAddedGameDTOs);
        userGamesSummary.setJustAddedCount(justAddedGameDTOs.size());

        int totalCount = playingGameDTOs.size()
                + completedGameDTOs.size()
                + pausedGameDTOs.size()
                + planningGameDTOs.size()
                + droppedGameDTOs.size()
                + justAddedGameDTOs.size();
        userGamesSummary.setTotalCount(totalCount);

        String listsOrder = "playing,completed,paused,planning,dropped,justAdded";
        //        Optional<HttpResponseModel> result = client.getUserCategoryListsInfoById(authorizationHeader);

        UserCategoryList result = userGRPCServiceClient.getUserCategoryListsInfoById(userId);
        if (result != null) {
            listsOrder = result.getCategoryList();
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
}
