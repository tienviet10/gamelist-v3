package com.gamelist.game_service.enums;

import lombok.*;

@Getter
public enum PlatformWebsiteType {

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
    DISCORD(15),
    GOOGLE_PLUS(16),
    TUMBLR(17),
    LINKEDIN(18),
    PINTEREST(19),
    SOUNDCLOUD(20);

    private final int id;

    PlatformWebsiteType(int id) {
        this.id = id;
    }
}