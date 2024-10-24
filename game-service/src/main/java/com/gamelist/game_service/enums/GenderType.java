package com.gamelist.game_service.enums;

import lombok.*;

@Getter
public enum GenderType {

    MALE(0),
    FEMALE(1),
    OTHER(2);

    private final int value;

    GenderType(int value) {
        this.value = value;
    }
}
