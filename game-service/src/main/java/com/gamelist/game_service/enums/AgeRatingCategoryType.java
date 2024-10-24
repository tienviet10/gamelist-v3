package com.gamelist.game_service.enums;

import lombok.*;

@Getter
public enum AgeRatingCategoryType {

    ESRB(1),
    PEGI(2),
    CERO(3),
    USK(4),
    GRAC(5),
    CLASS_IND(6),
    ACB(7);

    private final int value;

    AgeRatingCategoryType(int value) {
        this.value = value;
    }
}
