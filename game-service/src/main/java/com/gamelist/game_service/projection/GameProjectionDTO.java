package com.gamelist.game_service.projection;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GameProjectionDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private double avgScore;
    private String description;
    private String imageUrl;
    private String bannerUrl;
    private String gameAdded;
    private LocalDateTime releaseDate;
    private int totalRating;
    private String platforms;
    private String genres;
    private String tags;
}
