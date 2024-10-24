package com.gamelist.game_service.enums;

public enum CharacterSpeciesType {

    HUMAN(0),
    ALIEN(1),
    ANIMAL(2),
    ANDROID(3),
    UNKNOWN(4);

    private final int value;

    CharacterSpeciesType(int value) {
        this.value = value;
    }
}
