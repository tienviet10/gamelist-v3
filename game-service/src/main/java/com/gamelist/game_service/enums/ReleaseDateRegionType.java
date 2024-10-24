package com.gamelist.game_service.enums;

import lombok.*;

@Getter
public enum ReleaseDateRegionType {

    EUROPE(1),
    NORTH_AMERICA(2),
    AUSTRALIA(3),
    NEW_ZEALAND(4),
    JAPAN(5),
    CHINA(6),
    ASIA(7),
    WORLDWIDE(8),
    KOREA(9),
    BRAZIL(10);

    private final int value;

    ReleaseDateRegionType(int value) {
        this.value = value;
    }
}
