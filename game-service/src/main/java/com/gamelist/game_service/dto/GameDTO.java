package com.gamelist.game_service.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class GameDTO {
    private Long id;
    private String name;
    private double avgScore;
    private String description;
    private String imageURL;
    private String bannerURL;
    private LocalDateTime releaseDate;
    private int totalRating;
    private List<String> platforms;
    private List<String> tags;
    private List<String> genres;
    private boolean gameAdded;
    private boolean gameLiked;
}
