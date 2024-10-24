package com.gamelist.game_service.enums;

import lombok.*;

@Getter
public enum CompanyDateCategory {

    YYYYMMMMDD(0),
    YYYYMMMM(1),
    YYYY(2),
    YYYYQ1(3),
    YYYYQ2(4),
    YYYYQ3(5),
    YYYYQ4(6),
    TBD(7);

    private final int value;

    CompanyDateCategory(int i) {
        this.value = i;
    }
}
