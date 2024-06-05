package com.gamelist.game_service.model;

import com.gamelist.game_service.entity.GameStatus;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EditUserGameRequest {
    private Long gameId;
    private GameStatus gameStatus;
    private String gameNote;
    private Boolean isPrivate;
    private Integer rating;
    private LocalDateTime completedDate;
    private LocalDateTime startDate;
}
