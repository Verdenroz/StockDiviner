package org.farmingdale.stockdiviner.model.analysis;

import org.farmingdale.stockdiviner.model.Indicator;
import org.farmingdale.stockdiviner.model.alphavantage.ImplAlphaVantageAPI;
import org.farmingdale.stockdiviner.model.alphavantage.MonthlyStockData;
import org.farmingdale.stockdiviner.model.zodiac.ZodiacCalculator;
import org.farmingdale.stockdiviner.model.zodiac.ZodiacSigns;

import java.time.LocalDate;
import java.util.*;

/**
 * This class is used to analyze the stock data based on the Zodiac Signs.
 * @see ZodiacSigns
 */
public class ZodiacAnalysis extends Analysis {
    private final ZodiacCalculator zodiacCalculator;
    private final MonthlyStockData monthlyStockData;
    private Map<ZodiacSigns, Map<LocalDate, Double>> priceSeries;

    public ZodiacAnalysis(String stockSymbol) throws Exception {
        super(stockSymbol);
        this.zodiacCalculator = ZodiacCalculator.getInstance();
        this.monthlyStockData = ImplAlphaVantageAPI.getInstance().getMonthlyTimeSeries(stockSymbol);
        this.analyze();
    }

    @Override
    void analyze() {
        Map<ZodiacSigns, Map<LocalDate, Double>> zodiacPrices = new HashMap<>();
        //
        monthlyStockData.getMonthlyTimeSeries().forEach((date, stock) -> {
            System.out.printf("date: %s, price %s | ", date, stock.getClose());
            if (date.isBefore(LocalDate.now())) {
                ZodiacSigns zodiac = zodiacCalculator.getZodiacSign(date.getMonthValue(), date.getDayOfMonth());
                if (zodiacPrices.containsKey(zodiac)) { // if zodiac is already in add more prices
                    zodiacPrices.get(zodiac).put(date, Double.parseDouble(stock.getClose()));
                } else {
                    Map<LocalDate, Double> dateAndPrices = new TreeMap<>();
                    dateAndPrices.put(date, Double.parseDouble(stock.getClose()));
                    zodiacPrices.put(zodiac, dateAndPrices);
                }
            }
        });
        System.out.println();
        ZodiacSigns bestZodiacSign = null;
        ZodiacSigns worstZodiacSign = null;
        double bestAveragePrice = Double.MIN_VALUE;
        double worstAveragePrice = Double.MAX_VALUE;

        for (Map.Entry<ZodiacSigns, Map<LocalDate, Double>> entry : zodiacPrices.entrySet()) {
            double averagePrice = entry.getValue().values().stream().mapToDouble(val -> val).average().orElse(0.0);
            this.analyses.put(entry.getKey(), Math.ceil(averagePrice * 100) / 100);
            if (averagePrice > bestAveragePrice) {
                bestAveragePrice = averagePrice;
                bestZodiacSign = entry.getKey();
            }
            if (averagePrice < worstAveragePrice) {
                worstAveragePrice = averagePrice;
                worstZodiacSign = entry.getKey();
            }
        }
        this.bestIndicator = bestZodiacSign;
        this.worstIndicator = worstZodiacSign;
        this.bestStat = Math.ceil(bestAveragePrice * 100) / 100;
        this.worstStat = Math.ceil(worstAveragePrice * 100) / 100;
        this.priceSeries = zodiacPrices;
    }

    public Map<ZodiacSigns, Map<LocalDate, Double>> getPriceSeries() {
        return this.priceSeries;
    }
}
