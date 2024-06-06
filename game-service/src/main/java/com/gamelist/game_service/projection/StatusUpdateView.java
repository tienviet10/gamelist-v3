package com.gamelist.game_service.projection;

import com.gamelist.game_service.entity.GameStatus;
import java.time.LocalDateTime;

public interface StatusUpdateView {
    Long getId();

    UserGameBasicView getUserGame();

    GameStatus getGameStatus();

    LocalDateTime getCreatedAt();

    //    List<LikeEntityView> getLikes();
    //
    //    List<CommentView> getComments();
}
