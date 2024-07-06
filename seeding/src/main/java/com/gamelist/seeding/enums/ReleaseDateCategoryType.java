package com.gamelist.seeding.enums;

import lombok.*;

@Getter
public enum ReleaseDateCategoryType {

    YYYYMMDD(0),
    YYYYMM(1),
    YYYY(2),
    YYYYQ1(3),
    YYYYQ2(4),
    YYYYQ3(5),
    YYYYQ4(6),
    TBD(7);

    private final int value;

    ReleaseDateCategoryType(int value) {
        this.value = value;
    }
}
