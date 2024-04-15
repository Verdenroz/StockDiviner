package org.farmingdale.stockdiviner.model.analysis;

import org.farmingdale.stockdiviner.model.alphavantage.ImplAlphaVantageAPI;
import org.farmingdale.stockdiviner.model.alphavantage.MonthlyStockData;
import org.farmingdale.stockdiviner.model.zodiac.ZodiacCalculator;
import org.farmingdale.stockdiviner.model.zodiac.ZodiacSigns;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ZodiacAnalysis extends Analysis {
    private final ZodiacCalculator zodiacCalculator;
    private final MonthlyStockData monthlyStockData;

    public ZodiacAnalysis(String stockSymbol) throws Exception {
        super(stockSymbol);
        this.zodiacCalculator = ZodiacCalculator.getInstance();
        this.monthlyStockData = ImplAlphaVantageAPI.getInstance().getMonthlyTimeSeries(stockSymbol);
        this.analyze();
    }

    @Override
    void analyze() {
        Map<ZodiacSigns, List<Double>> zodiacPrices = new HashMap<>();

        monthlyStockData.getMonthlyTimeSeries().forEach((date, stock) -> {
            if (date.isBefore(LocalDate.now())) {
                ZodiacSigns zodiac = zodiacCalculator.getZodiacSign(date.getMonthValue(), date.getDayOfMonth());
                if (zodiacPrices.containsKey(zodiac)) {
                    zodiacPrices.get(zodiac).add(Double.parseDouble(stock.getClose()));
                } else {
                    List<Double> prices = new ArrayList<>();
                    prices.add(Double.parseDouble(stock.getClose()));
                    zodiacPrices.put(zodiac, prices);
                }
            }
        });

        ZodiacSigns bestZodiacSign = null;
        ZodiacSigns worstZodiacSign = null;
        double bestAveragePrice = zodiacPrices.get(ZodiacSigns.AQUARIUS).stream().mapToDouble(val -> val).average().orElse(0.0);
        double worstAveragePrice = zodiacPrices.get(ZodiacSigns.AQUARIUS).stream().mapToDouble(val -> val).average().orElse(0.0);

        for (Map.Entry<ZodiacSigns, List<Double>> entry : zodiacPrices.entrySet()) {
            double averagePrice = entry.getValue().stream().mapToDouble(val -> val).average().orElse(0.0);
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
    }
}
