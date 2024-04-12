package org.farmingdale.stockdiviner;

import org.farmingdale.stockdiviner.model.animals.ChineseNewYears;
import org.farmingdale.stockdiviner.model.financialmodeling.FullQuoteData;


import org.farmingdale.stockdiviner.model.lunar.LunarCalculatorAPI;
import org.farmingdale.stockdiviner.model.zodiac.ZodiacCalculator;

public class SharedService {
    private static SharedService instance = null;
    private FullQuoteData data;

    private ChineseNewYears chineseNewYears;

    private LunarCalculatorAPI lunarPhanses;

    private ZodiacCalculator zodiacCalculator;

    private SharedService() {
    }

    public static SharedService getInstance() {
        if (instance == null) {
            instance = new SharedService();
        }
        return instance;
    }

    public FullQuoteData getData() {
        return data;
    }

    public void setData(FullQuoteData data) {
        this.data = data;
    }

    public ChineseNewYears getChineseNewYears() {
        return chineseNewYears;
    }
    public void setChineseNewYears(ChineseNewYears chineseNewYears) {
        this.chineseNewYears = chineseNewYears;
    }
    public LunarCalculatorAPI getLunarPhanses() {
        return lunarPhanses;
    }
    public void setLunarPhanses(LunarCalculatorAPI lunarPhanses) {
        this.lunarPhanses = lunarPhanses;
    }
    public ZodiacCalculator getZodiacCalulator() {
        return zodiacCalculator;
    }
    public void setZodiacCalulator(ZodiacCalculator zodiacCalculator) {
        this.zodiacCalculator = zodiacCalculator;
    }

}
