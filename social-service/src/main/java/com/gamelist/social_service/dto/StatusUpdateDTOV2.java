package com.gamelist.social_service.dto;

import com.gamelist.social_service.clients.user.UserDTO;
import com.gamelist.social_service.entity.GameStatus;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class StatusUpdateDTOV2 {
    private Long id;
    private GameStatus gameStatus;
    private LocalDateTime createdAt;
    private Long userGameId;
    private String userGameUserId;
    private Long gameId;
    private String gameName;
    private String gameImageUrl;
    private String gameBannerUrl;
    private UserDTO user;
    private List<LikeEntityDTO> likes;
    private List<CommentDTO> comments;
}
