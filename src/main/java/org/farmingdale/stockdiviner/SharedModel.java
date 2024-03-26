package org.farmingdale.stockdiviner;

import org.farmingdale.stockdiviner.model.animals.ChineseNewYears;
import org.farmingdale.stockdiviner.model.lunar.LunarCalculatorAPI;

public class SharedModel {
    private static SharedModel instance;

    private ChineseNewYears chineseNewYears;
    private LunarCalculatorAPI lunarPhases;
    // Other shared instances

    private SharedModel() {}

    public static SharedModel getInstance() {
        if (instance == null) {
            instance = new SharedModel();
        }
        return instance;
    }

    public void setChineseNewYears(ChineseNewYears chineseNewYears) {
        this.chineseNewYears = chineseNewYears;
    }

    public ChineseNewYears getChineseNewYears() {
        return chineseNewYears;
    }

    public void setLunarPhases(LunarCalculatorAPI lunarPhases) {
        this.lunarPhases = lunarPhases;
    }

    public LunarCalculatorAPI getLunarPhases() {
        return lunarPhases;
    }
}
