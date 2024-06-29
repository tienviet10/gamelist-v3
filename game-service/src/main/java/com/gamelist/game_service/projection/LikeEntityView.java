package com.gamelist.game_service.projection;

import java.time.LocalDateTime;

public interface LikeEntityView {
    Long getId();

    String getUserId();

    LocalDateTime getUpdatedAt();
}
