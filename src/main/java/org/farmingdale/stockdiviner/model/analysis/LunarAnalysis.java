package org.farmingdale.stockdiviner.model.analysis;

import org.farmingdale.stockdiviner.model.alphavantage.ImplAlphaVantageAPI;
import org.farmingdale.stockdiviner.model.alphavantage.WeeklyStockData;
import org.farmingdale.stockdiviner.model.lunar.ImplLunarCalculatorAPI;
import org.farmingdale.stockdiviner.model.lunar.LunarPhase;
import org.farmingdale.stockdiviner.model.zodiac.ZodiacSigns;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * This class is used to analyze the stock data based on the Lunar Phases.
 * @see LunarPhase
 */
public class LunarAnalysis extends Analysis {
    private final ImplLunarCalculatorAPI lunarCalculator;
    private final WeeklyStockData weeklyStockData;
    private Map<LunarPhase, Map<LocalDate, Double>> priceSeries;

    public LunarAnalysis(String stockSymbol) throws Exception {
        super(stockSymbol);
        this.lunarCalculator = ImplLunarCalculatorAPI.getInstance();
        this.weeklyStockData = ImplAlphaVantageAPI.getInstance().getWeeklyTimeSeries(stockSymbol);
        this.analyze();
    }

    @Override
    void analyze() throws Exception {
        Map<LocalDate, LunarPhase> lunarPhases = lunarCalculator.getLunarPhase(LocalDate.now().minusYears(2));
        Map<LocalDate, Double> lunarPrices = new TreeMap<>();

        //gets the closest date to the lunar phase date
        for (LocalDate lunarDate : lunarPhases.keySet()) {
            LocalDate closestDate = weeklyStockData.getWeeklyTimeSeries().keySet().stream()
                    .filter(stockDate -> !stockDate.isAfter(lunarDate))
                    .max(LocalDate::compareTo)
                    .orElse(null);

            if (closestDate != null) {
                double price = Double.parseDouble(weeklyStockData.getWeeklyTimeSeries().get(closestDate).getClose());
                lunarPrices.put(lunarDate, price);
            }
        }

        Map<LunarPhase, Map<LocalDate, Double>> lunarPhasePrices = new HashMap<>();

        //maps the lunar prices to the lunar phase
        for (Map.Entry<LocalDate, Double> entry : lunarPrices.entrySet()) {
            LunarPhase phase = lunarPhases.get(entry.getKey());
            lunarPhasePrices.computeIfAbsent(phase, k -> new TreeMap<>()).put(entry.getKey(), entry.getValue());
        }
        //System.out.println("lunarPhasePrices after mapping prices: " + lunarPhasePrices);

        LunarPhase bestLunarPhase = null;
        LunarPhase worstLunarPhase = null;
        double bestAveragePrice = Double.MIN_VALUE;
        double worstAveragePrice = Double.MAX_VALUE;

        //calculates the average price for each lunar phase
        for (Map.Entry<LunarPhase, Map<LocalDate, Double>> entry : lunarPhasePrices.entrySet()) {
            double averagePrice = entry.getValue().values().stream().mapToDouble(val -> val).average().orElse(0.0);
            //System.out.println("Average price for " + entry.getKey() + ": " + averagePrice);
            this.analyses.put(entry.getKey(), Math.ceil(averagePrice * 100.0) / 100.0);

            if (averagePrice > bestAveragePrice) {
                bestAveragePrice = averagePrice;
                bestLunarPhase = entry.getKey();
            }
            if (averagePrice < worstAveragePrice) {
                worstAveragePrice = averagePrice;
                worstLunarPhase = entry.getKey();
            }
        }
        this.bestIndicator = bestLunarPhase;
        this.worstIndicator = worstLunarPhase;
        this.bestStat = Math.ceil(bestAveragePrice * 100.0) / 100.0;
        this.worstStat = Math.ceil(worstAveragePrice * 100.0) / 100.0;
        this.priceSeries = lunarPhasePrices;
    }
    public Map<LunarPhase, Map<LocalDate, Double>> getPriceSeries() {
        return this.priceSeries;
    }

}
