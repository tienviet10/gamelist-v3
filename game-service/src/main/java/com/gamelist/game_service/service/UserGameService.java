package com.gamelist.game_service.service;

import com.gamelist.game_service.dto.UserGamesSummaryDTO;
import com.gamelist.game_service.entity.UserGame;
import com.gamelist.game_service.model.EditUserGameRequest;
import java.util.Set;

public interface UserGameService {
    //    UserGame findUserGameById(Long requestedId, User principal);

    UserGame createUserGame(EditUserGameRequest userGame, Long userId);

    UserGame updateUserGameById(EditUserGameRequest userGame, Long userId);

    //    UserGame deleteUserGameById(Long requestedId, User principal);

    Set<UserGame> findAllUserGamesByUserId(Long userId);

    UserGamesSummaryDTO findAllUserGamesByUserIdByStatus(Long userId);

    UserGame findUserGameByGameId(Long gameId, Long userId);

    UserGame deleteUserGameByGameId(Long gameId, Long userId);
}
