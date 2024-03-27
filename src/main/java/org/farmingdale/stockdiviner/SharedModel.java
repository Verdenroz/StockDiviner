package org.farmingdale.stockdiviner;

import org.farmingdale.stockdiviner.model.animals.ChineseNewYears;
import org.farmingdale.stockdiviner.model.lunar.LunarCalculatorAPI;

public class SharedModel {
    private static SharedModel instance;

    private ApiType selectedApiType;
    private ChineseNewYears chineseNewYears;
    private LunarCalculatorAPI lunarPhases;
    // Other API instances as needed

    private SharedModel() {}

    public static SharedModel getInstance() {
        if (instance == null) {
            instance = new SharedModel();
        }
        return instance;
    }

    public void selectApi(ApiType apiType) {
        this.selectedApiType = apiType;
    }

    public Object getSelectedApi() {
        switch (selectedApiType) {
            case CHINESE_NEW_YEARS:
                return getChineseNewYears();
            case LUNAR_PHASES:
                return getLunarPhases();
            // Add cases for other APIs
            default:
                return null;
        }
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
