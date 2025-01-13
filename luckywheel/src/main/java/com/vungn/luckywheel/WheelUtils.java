package com.vungn.luckywheel;

import static java.lang.Math.random;

import java.util.ArrayList;
import java.util.List;

public class WheelUtils {
    public static int calculateTotalProbability(List<WheelItem> wheelItems) {
        int totalProbability = 0;
        for (WheelItem item : wheelItems) {
            totalProbability += item.getProbability();
        }
        return totalProbability;
    }

    public static List<WheelItem> generateListBasedOnProbability(List<WheelItem> wheelItems) {
        List<WheelItem> expandedList = new ArrayList<>();
        for (WheelItem item : wheelItems) {
            for (int i = 0; i < item.getProbability(); i++) {
                expandedList.add(item);
            }
        }
        return expandedList;
    }

    public static int getRandomIndex(List<WheelItem> wheelItems) {
        return (int) (random() * calculateTotalProbability(wheelItems));
    }

    public static List<WheelItem> generateListBaseOnSliceRepeat(List<WheelItem> wheelItems, int sliceRepeat) {
        List<WheelItem> expandedList = new ArrayList<>();
        for (int i = 0; i < sliceRepeat; i++) {
            expandedList.addAll(generateListBasedOnProbability(wheelItems));
        }
        return expandedList;
    }
}
