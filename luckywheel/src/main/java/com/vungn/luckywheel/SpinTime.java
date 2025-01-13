package com.vungn.luckywheel;

/**
 * Created by Vũ Nguyễn on 10/01/2025.
 */

public enum SpinTime {
    X1(1000),
    X2(2000),
    X3(4000),
    X4(6000),
    X5(10000),
    ;

    public final int value;

    SpinTime(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
