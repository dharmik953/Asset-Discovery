package com.serviceops.assetdiscovery.entity.enums;

public enum Month {
    JANUARY (1),
    FEBRUARY (2),
    MARCH (3),
    APRIL (4),
    MAY(5),
    JUNE (6),
    JULY (7),
    AUGUST (8),
    SEPTEMBER (9),
    OCTOBER (10),
    NOVEMBER (11),
    DECEMBER (12);
    private final int code;

    private Month(int code) {
        this.code = code;
    }

    public int toInt() {
        return code;
    }

    public String toString() {
        //only override toString, if the returned value has a meaning for the
        //human viewing this value
        return String.valueOf(code);
    }
}