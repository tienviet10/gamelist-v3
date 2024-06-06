package com.gamelist.social_service.dto;

import com.gamelist.social_service.entity.GameStatus;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class StatusUpdateDTO {
    private Long id;
    private UserGameDTO userGame;
    private GameStatus gameStatus;
    private LocalDateTime createdAt;
    private List<LikeEntityDTO> likes;
    private List<CommentDTO> comments;
}
