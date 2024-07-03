package com.gamelist.game_service.dto;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class UserGamesSummaryDTO {
    private List<UserGamesDTO> playing;
    private int playingCount;

    private List<UserGamesDTO> completed;
    private int completedCount;

    private List<UserGamesDTO> paused;
    private int pausedCount;

    private List<UserGamesDTO> planning;
    private int planningCount;

    private List<UserGamesDTO> dropped;
    private int droppedCount;

    private List<UserGamesDTO> justAdded;
    private int justAddedCount;

    private int totalCount;

    private String listsOrder;
}
