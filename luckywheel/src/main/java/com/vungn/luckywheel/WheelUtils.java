package com.vungn.luckywheel;

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
}
