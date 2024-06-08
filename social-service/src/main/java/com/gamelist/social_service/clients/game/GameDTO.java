package com.gamelist.social_service.clients.game;

import java.time.LocalDateTime;
import java.util.List;

public record GameDTO(
        Long id,
        String name,
        double avgScore,
        String description,
        String imageURL,
        String bannerURL,
        LocalDateTime releaseDate,
        int totalRating,
        List<String> platforms,
        List<String> tags,
        List<String> genres,
        boolean gameAdded,
        boolean gameLiked) {}
