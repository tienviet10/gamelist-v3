package com.gamelist.seeding.enums;

import lombok.*;

@Getter
public enum GameStatusType {

    RELEASED(0),
    ALPHA(1),
    BETA(2),
    EARLY_ACCESS(3),
    OFFLINE(4),
    CANCELLED(6),
    RUMORED(7),
    DELISTED(8);

    private final int value;

    GameStatusType(int value) {
        this.value = value;
    }
}
