package com.gamelist.game_service.enums;

import lombok.*;

@Getter
public enum WebsiteType {

    OFFICIAL(1),
    WIKIA(2),
    WIKIPEDIA(3),
    FACEBOOK(4),
    TWITTER(5),
    TWITCH(6),
    INSTAGRAM(8),
    YOUTUBE(9),
    IPHONE(10),
    IPAD(11),
    ANDROID(12),
    STEAM(13),
    REDDIT(14),
    ITCH(15),
    EPICGAMES(16),
    GOG(17),
    DISCORD(18);

    private final int value;

    WebsiteType(int value) {
        this.value = value;
    }
}
