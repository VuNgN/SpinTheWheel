package com.vungn.luckywheel;

/**
 * Created by Vũ Nguyễn on 10/01/2025.
 */

public interface WheelListener {
    /**
     * Function to call when wheel is sliding
     */
    void onSlideTheWheel();

    /**
     * Function to call when wheel is spinning
     */
    void onTouchTheSpin();
}
