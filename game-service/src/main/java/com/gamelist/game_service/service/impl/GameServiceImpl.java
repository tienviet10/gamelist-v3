package com.gamelist.game_service.service.impl;

import com.gamelist.game_service.dto.GameDTO;
import com.gamelist.game_service.mapper.GameMapper;
import com.gamelist.game_service.repository.GameRepository;
import com.gamelist.game_service.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;
    private final GameMapper gameMapper;

    @Override
    public GameDTO getAGame(Long gameId) {
        return gameMapper.gameToGameDTO(gameRepository.findById(gameId).orElseThrow());
    }
}
