package com.gamelist.social_service.projection;

import java.time.LocalDateTime;

public interface InteractiveEntityProjection {
    Long getId();

    LocalDateTime getCreatedAt();

    String getUserId();

    String getPostText();

    Long getStatusUpdateId();

    Long getSuUsergameId();

    String getSuGameStatus();

    Long getUserGameId();

    String getUserGameUserId();

    Long getGameId();

    String getGameName();

    String getGameImageurl();

    String getGameBannerurl();

    String getLikes();

    String getComments();
}
