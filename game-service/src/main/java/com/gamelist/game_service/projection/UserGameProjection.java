package com.gamelist.game_service.projection;

import java.time.LocalDateTime;

public interface UserGameProjection {
    Long getId();

    String getName();

    double getAvgScore();

    String getDescription();

    String getImageURL();

    String getBannerURL();

    LocalDateTime getReleaseDate();

    int getTotalRating();

    String getTags();

    String getPlatforms();

    String getGenres();

    String getGameAdded();

    String getGameStatus();

    boolean getGameliked();
}
