package com.gamelist.game_service.service.impl;

import com.gamelist.game_service.dto.GameDTO;
import com.gamelist.game_service.repository.GameRepository;
import com.gamelist.game_service.service.GameService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;
    //    private final GameMapper gameMapper;

    @Override
    public GameDTO getAGame(Long gameId) {
        //        return gameMapper.gameToGameDTO(gameRepository.findById(gameId).orElseThrow());
        GameDTO gameDTO = new GameDTO();
        gameDTO.setId(1L);
        gameDTO.setName("Game 1");
        gameDTO.setAvgScore(4.5);
        gameDTO.setDescription("Description 1");
        gameDTO.setImageURL("Image URL 1");
        gameDTO.setBannerURL("Banner URL 1");
        gameDTO.setReleaseDate(LocalDateTime.now());
        gameDTO.setTotalRating(100);
        gameDTO.setPlatforms(List.of("Platform 1", "Platform 2"));
        gameDTO.setTags(List.of("Tag 1", "Tag 2"));
        gameDTO.setGenres(List.of("Genre 1", "Genre 2"));
        gameDTO.setGameAdded(true);
        gameDTO.setGameLiked(true);
        return gameDTO;
    }
}
