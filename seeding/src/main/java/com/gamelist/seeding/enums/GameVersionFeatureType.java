package com.gamelist.seeding.enums;

import lombok.*;

@Getter
public enum GameVersionFeatureType {

    BOOLEAN(0),
    DESCRIPTION(1);

    private final int value;

    GameVersionFeatureType(int value) {
        this.value = value;
    }
}
