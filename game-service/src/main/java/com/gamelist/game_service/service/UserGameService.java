package com.gamelist.game_service.service;

import com.gamelist.game_service.dto.UserGamesSummaryDTO;
import com.gamelist.game_service.entity.UserGame;
import com.gamelist.game_service.model.EditUserGameRequest;
import java.util.Set;

public interface UserGameService {
    //    //    UserGame findUserGameById(Long requestedId, User principal);

    UserGame createUserGame(EditUserGameRequest userGame, String userId);

    UserGame updateUserGameById(EditUserGameRequest userGame, String userId);

    //    //    UserGame deleteUserGameById(Long requestedId, User principal);

    Set<UserGame> findAllUserGamesByUserId(String userId);

    UserGamesSummaryDTO findAllUserGamesByUserIdByStatus(String userId, String authorizationHeader);

    UserGame findUserGameByGameId(Long gameId, String userId);

    UserGame deleteUserGameByGameId(Long gameId, String userId);
}
