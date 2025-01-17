package com.gamelist.game_service.projection;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameProjectionImpl implements GameProjection, Serializable {
    private Long id;
    private String name;
    private double avgScore;
    private String description;
    private String imageurl;
    private String bannerurl;
    private String gameAdded;
    private LocalDateTime releasedate;
    private int totalRating;
    private String platforms;
    private String genres;
    private String tags;
}
