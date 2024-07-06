package com.gamelist.seeding.enums;

import lombok.*;

@Getter
public enum IncludedFeatureType {

    NOT_INCLUDED(0),
    INCLUDED(1),
    PRE_ORDER_ONLY(2);

    private final int value;

    IncludedFeatureType(int value) {
        this.value = value;
    }
}
