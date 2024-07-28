package com.gamelist.game_service.projection;

import java.time.LocalDateTime;

public interface GameProjection {
    Long getId();

    String getName();

    double getAvgScore();

    String getDescription();

    String getImageurl();

    String getBannerurl();

    String getGameAdded();

    LocalDateTime getReleasedate();

    int getTotalRating();

    String getPlatforms();

    String getGenres();

    String getTags();
}
