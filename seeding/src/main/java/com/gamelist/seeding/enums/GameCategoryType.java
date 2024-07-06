package com.gamelist.seeding.enums;

import lombok.*;

@Getter
public enum GameCategoryType {

    MAIN_GAME(0, "Main Game"),
    DLC_ADDON(1, "DLC Addon"),
    EXPANSION(2, "Expansion"),
    BUNDLE(3, "Bundle"),
    STANDALONE_EXPANSION(4, "Standalone Expansion"),
    MOD(5, "Mod"),
    EPISODE(6, "Episode"),
    SEASON(7, "Season"),
    REMAKE(8, "Remake"),
    REMASTER(9, "Remaster"),
    EXPANDED_GAME(10, "Expanded Game"),
    PORT(11, "Port"),
    FORK(12, "Fork"),
    PACK(13, "Pack"),
    UPDATE(14, "Update");

    private final int value;
    private final String name;

    GameCategoryType(int value, String name) {
        this.value = value;
        this.name = name;
    }
}

