package com.vungn.luckywheel;

/**
 * Created by Vũ Nguyễn on 10/01/2025.
 */

public interface OnLuckyWheelReachTheTarget {
    void onReachFinalTarget(WheelItem wheelItem);

    void onTargetChanged(WheelItem wheelItem);
}
