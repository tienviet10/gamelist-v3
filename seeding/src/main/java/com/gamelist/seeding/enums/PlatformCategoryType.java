package com.gamelist.seeding.enums;

import lombok.*;

@Getter
public enum PlatformCategoryType {

    CONSOLE(1),
    ARCADE(2),
    PLATFORM(3),
    OPERATING_SYSTEM(4),
    PORTABLE_CONSOLE(5),
    COMPUTER(6);

    private final int value;

    PlatformCategoryType(int value) {
        this.value = value;
    }
}
