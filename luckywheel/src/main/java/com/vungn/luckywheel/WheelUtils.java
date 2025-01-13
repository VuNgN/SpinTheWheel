package com.vungn.luckywheel;

import static java.lang.Math.random;

import java.util.ArrayList;
import java.util.List;

public class WheelUtils {

    /**
     * Function to calculate total probability of wheel items
     *
     * @param wheelItems Wheel items
     * @return Total probability
     */
    public static int calculateTotalProbability(List<WheelItem> wheelItems) {
        int totalProbability = 0;
        for (WheelItem item : wheelItems) {
            totalProbability += item.getProbability();
        }
        return totalProbability;
    }

    /**
     * Function to generate list based on probability
     *
     * @param wheelItems Wheel items
     * @return Expanded list
     */
    public static List<WheelItem> generateListBasedOnProbability(List<WheelItem> wheelItems) {
        List<WheelItem> expandedList = new ArrayList<>();
        for (WheelItem item : wheelItems) {
            for (int i = 0; i < item.getProbability(); i++) {
                expandedList.add(item);
            }
        }
        return expandedList;
    }

    /**
     * Function to generate list based on slice repeat
     *
     * @param wheelItems  Wheel items
     * @param sliceRepeat Slice repeat
     * @return Expanded list
     */
    public static List<WheelItem> generateListBaseOnSliceRepeat(List<WheelItem> wheelItems, int sliceRepeat) {
        List<WheelItem> expandedList = new ArrayList<>();
        for (int i = 0; i < sliceRepeat; i++) {
            expandedList.addAll(generateListBasedOnProbability(wheelItems));
        }
        return expandedList;
    }

    /**
     * Function to get random index
     *
     * @param wheelItems Wheel items
     * @return Random index
     */
    public static int getRandomIndex(List<WheelItem> wheelItems) {
        return (int) (random() * calculateTotalProbability(wheelItems));
    }
}
