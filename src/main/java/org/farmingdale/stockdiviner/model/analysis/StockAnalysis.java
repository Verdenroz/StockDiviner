package org.farmingdale.stockdiviner.model.analysis;

import javafx.util.Pair;
import org.farmingdale.stockdiviner.model.alphavantage.AlphaVantageAPI;
import org.farmingdale.stockdiviner.model.alphavantage.ImplAlphaVantageAPI;
import org.farmingdale.stockdiviner.model.alphavantage.MonthlyStockData;
import org.farmingdale.stockdiviner.model.alphavantage.WeeklyStockData;
import org.farmingdale.stockdiviner.model.animals.ChineseAnimals;
import org.farmingdale.stockdiviner.model.animals.ChineseNewYears;
import org.farmingdale.stockdiviner.model.financialmodeling.FinancialModelingAPI;
import org.farmingdale.stockdiviner.model.financialmodeling.ImplFinancialModelingAPI;
import org.farmingdale.stockdiviner.model.lunar.ImplLunarCalculatorAPI;
import org.farmingdale.stockdiviner.model.lunar.LunarCalculatorAPI;
import org.farmingdale.stockdiviner.model.lunar.LunarPhase;
import org.farmingdale.stockdiviner.model.zodiac.ZodiacCalulator;
import org.farmingdale.stockdiviner.model.zodiac.ZodiacSigns;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StockAnalysis {
    private final MonthlyStockData monthlyStockData;

    private final WeeklyStockData weeklyStockData;

    private final ChineseAnimals bestAnimal;

    private final double bestAnimalAvg;

    private final ChineseAnimals worstAnimal;

    private final double worstAnimalAvg;

    private final ZodiacSigns bestZodiacSign;

    private final double bestZodiacPercent;

    private final ZodiacSigns worstZodiacSign;

    private final double worstZodiacPercent;

    private final LunarPhase bestLunarPhase;

    private final double bestLunarAvg;

    private final LunarPhase worstLunarPhase;

    private final double worstLunarAvg;

    private static final AlphaVantageAPI alphaVantage = ImplAlphaVantageAPI.getInstance();

    private static final FinancialModelingAPI financialModeling = ImplFinancialModelingAPI.getInstance();

    private static final ChineseNewYears chineseNewYears = ChineseNewYears.getInstance();

    private static final LunarCalculatorAPI lunarCalculator = ImplLunarCalculatorAPI.getInstance();

    private static final ZodiacCalulator zodiacCalculator = ZodiacCalulator.getInstance();

    public StockAnalysis(String symbol) throws IOException {
        monthlyStockData = alphaVantage.getMonthlyTimeSeries(symbol);
        weeklyStockData = alphaVantage.getWeeklyTimeSeries(symbol);
        LocalDate date = LocalDate.now();

        Pair<Pair<ChineseAnimals, ChineseAnimals>, Pair<Double, Double>> bestWorstAnimals = getBestWorstAnimal(date);
        bestAnimal = bestWorstAnimals.getKey().getKey();
        worstAnimal = bestWorstAnimals.getKey().getValue();
        bestAnimalAvg = Math.ceil(bestWorstAnimals.getValue().getKey() * 100) / 100;
        worstAnimalAvg = Math.ceil(bestWorstAnimals.getValue().getValue() * 100) / 100;

        Pair<Pair<ZodiacSigns, ZodiacSigns>, Pair<Double, Double>> bestWorstZodiacs = getBestWorstZodiacs(date);
        bestZodiacSign = bestWorstZodiacs.getKey().getKey();
        worstZodiacSign = bestWorstZodiacs.getKey().getValue();
        bestZodiacPercent = Math.ceil(bestWorstZodiacs.getValue().getKey() * 100) / 100;
        worstZodiacPercent = Math.ceil(bestWorstZodiacs.getValue().getValue() * 100) / 100;

        Pair<Pair<LunarPhase, LunarPhase>, Pair<Double, Double>> bestWorstLunar = getBestWorstLunar(date);
        bestLunarPhase = bestWorstLunar.getKey().getKey();
        worstLunarPhase = bestWorstLunar.getKey().getKey();
        bestLunarAvg = Math.ceil(bestWorstLunar.getValue().getKey() * 100) / 100;
        worstLunarAvg = Math.ceil(bestWorstLunar.getValue().getValue() * 100) / 100;
    }

    private Pair<Pair<ChineseAnimals, ChineseAnimals>, Pair<Double, Double>> getBestWorstAnimal(LocalDate currentDate) {
        // Convert the keySet to an ArrayList
        ArrayList<LocalDate> keys = new ArrayList<>(monthlyStockData.getMonthlyTimeSeries().keySet());

        // Get the earliest time series date
        LocalDate lastKey = keys.getLast();

        double bestPercentIncrease = 0;
        double worstPercentIncrease = 0;

        LocalDate startDate = currentDate.minusYears(12);

        // If the start date is before the first key, increment by 1 year until it is after the last key
        while (startDate.isBefore(lastKey)) {
            startDate = startDate.plusYears(1);
        }

        ChineseAnimals bestChineseAnimal = null;
        ChineseAnimals worstChineseAnimal = null;

        for (LocalDate date = startDate; !date.isAfter(currentDate.minusYears(1)); date = date.plusYears(1)) {
            LocalDate startYear = LocalDate.of(date.getYear() - 1, 12, 31);
            LocalDate endYear = LocalDate.of(date.getYear(), 12, 31);
            ChineseAnimals animal = chineseNewYears.getChineseZodiac(endYear.getYear());

            while (!monthlyStockData.getMonthlyTimeSeries().containsKey(endYear)) {
                endYear = endYear.minusDays(1);
            }
            while (!monthlyStockData.getMonthlyTimeSeries().containsKey(startYear)) {
                startYear = startYear.minusDays(1);
            }

            try {
                double startPrice = Double.parseDouble(monthlyStockData.getMonthlyTimeSeries().get(startYear).getClose());
                double endPrice = Double.parseDouble(monthlyStockData.getMonthlyTimeSeries().get(endYear).getClose());

                double percentIncrease = (endPrice - startPrice) / startPrice;

                //System.out.println(endYear.getYear() + " " + animal + " " + startYear + " " + startPrice + " " + endYear + " " + endPrice + " " + percentIncrease);

                if (percentIncrease > bestPercentIncrease) {
                    bestPercentIncrease = percentIncrease;
                    bestChineseAnimal = animal;
                }
                if (percentIncrease < worstPercentIncrease) {
                    worstPercentIncrease = percentIncrease;
                    worstChineseAnimal = animal;
                }
            } catch (Exception e) {
                //System.out.println("Error: " + e.getMessage());
            }
        }
        Pair<ChineseAnimals, ChineseAnimals> bestWorstAnimals = new Pair<>(bestChineseAnimal, worstChineseAnimal);
        Pair<Double, Double> bestWorstPercent = new Pair<>(bestPercentIncrease, worstPercentIncrease);
        return new Pair<>(bestWorstAnimals, bestWorstPercent);
    }

    private Pair<Pair<ZodiacSigns, ZodiacSigns>, Pair<Double, Double>> getBestWorstZodiacs(LocalDate currentDate) {
        Map<ZodiacSigns, List<Double>> zodiacPrices = new HashMap<>();

        monthlyStockData.getMonthlyTimeSeries().forEach((date, stock) -> {
            if (date.isBefore(currentDate)) {
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
            if (averagePrice > bestAveragePrice) {
                bestAveragePrice = averagePrice;
                bestZodiacSign = entry.getKey();
            }
            if (averagePrice < worstAveragePrice) {
                worstAveragePrice = averagePrice;
                worstZodiacSign = entry.getKey();
            }
        }
        Pair<ZodiacSigns, ZodiacSigns> bestWorstZodiacs = new Pair<>(bestZodiacSign, worstZodiacSign);
        Pair<Double, Double> bestWorstPercent = new Pair<>(bestAveragePrice, worstAveragePrice);
        return new Pair<>(bestWorstZodiacs, bestWorstPercent);
    }

    private Pair<Pair<LunarPhase, LunarPhase>, Pair<Double,Double>> getBestWorstLunar(LocalDate currentDate) throws IOException {
        Map<LocalDate, LunarPhase> lunarPhases = lunarCalculator.getLunarPhase(currentDate.minusYears(2));
        Map<LocalDate, Double> lunarPrices = new HashMap<>();

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
            lunarPhasePrices.computeIfAbsent(phase, k -> new HashMap<>()).put(entry.getKey(), entry.getValue());
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

            if (averagePrice > bestAveragePrice) {
                bestAveragePrice = averagePrice;
                bestLunarPhase = entry.getKey();
            }
            if (averagePrice < worstAveragePrice) {
                worstAveragePrice = averagePrice;
                worstLunarPhase = entry.getKey();
            }
        }
        Pair<LunarPhase, LunarPhase>  bestWorstLunar = new Pair<>(bestLunarPhase, worstLunarPhase);
        Pair<Double, Double> bestWorstPercent = new Pair<>(bestAveragePrice, worstAveragePrice);
        return new Pair<>(bestWorstLunar, bestWorstPercent);
    }

    public ChineseAnimals getBestAnimal() {
        return bestAnimal;
    }

    public ChineseAnimals getWorstAnimal() {
        return worstAnimal;
    }

    public LunarPhase getBestLunarPhase() {
        return bestLunarPhase;
    }

    public LunarPhase getWorstLunarPhase() {
        return worstLunarPhase;
    }

    public ZodiacSigns getBestZodiacSign() {
        return bestZodiacSign;
    }

    public ZodiacSigns getWorstZodiacSign() {
        return worstZodiacSign;
    }

    public double getBestZodiacPercent() {
        return bestZodiacPercent;
    }

    public double getWorstZodiacPercent() {
        return worstZodiacPercent;
    }

    public double getBestAnimalAvg() {
        return bestAnimalAvg;
    }

    public double getWorstAnimalAvg() {
        return worstAnimalAvg;
    }

    public double getBestLunarAvg() {
        return bestLunarAvg;
    }

    public double getWorstLunarAvg() {
        return worstLunarAvg;
    }
}
