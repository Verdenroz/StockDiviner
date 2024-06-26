package org.farmingdale.stockdiviner.model.analysis;

import org.farmingdale.stockdiviner.model.alphavantage.MonthlyStockData;
import org.farmingdale.stockdiviner.model.animals.ChineseAnimals;
import org.farmingdale.stockdiviner.model.animals.ChineseNewYears;
import org.farmingdale.stockdiviner.model.lunar.LunarPhase;
import org.farmingdale.stockdiviner.model.zodiac.ZodiacSigns;

import java.time.LocalDate;
import java.util.*;

/**
 * This class is used to analyze the stock data based on the Chinese Zodiac.
 * @see ChineseAnimals
 */
public class AnimalAnalysis extends Analysis {
    private final MonthlyStockData monthlyStockData;
    private final ChineseNewYears chineseNewYears;
    private final Map<Integer, ChineseAnimals> animalDates;
    private Map<ChineseAnimals, Map<LocalDate, Double>> priceSeries;


    public AnimalAnalysis(String stockSymbol) throws Exception {
        super(stockSymbol);
        this.monthlyStockData = api.getMonthlyTimeSeries(stockSymbol);
        this.chineseNewYears = ChineseNewYears.getInstance();
        this.animalDates = initializeAnimalDates();
        this.analyze();
    }

    @Override
    void analyze() {
        priceSeries = new HashMap<ChineseAnimals, Map<LocalDate, Double>>();
        LocalDate currentDate = LocalDate.now();
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
//                System.out.printf(animal + " ?end year? " + endYear + " | ");
            }
            System.out.println();
            while (!monthlyStockData.getMonthlyTimeSeries().containsKey(startYear)) {
                startYear = startYear.minusDays(1);
//                System.out.printf(animal + " ?start year? " + startYear + " | ");
            }
//            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//            System.out.println(animal + ", " + startYear + ", " + endYear);

            Map<LocalDate, Double> animalPriceSeries = new TreeMap<LocalDate, Double>();
            LocalDate finalStartDate = startYear;
            LocalDate finalEndYear = endYear;
//            System.out.println("final start date "+finalStartDate+", final end date "+finalEndYear);
            monthlyStockData.getMonthlyTimeSeries().forEach((month, stock) -> {
                        if (month.isAfter(finalStartDate) && month.isBefore(finalEndYear)) {
                            animalPriceSeries.put(month, Double.parseDouble(stock.getClose()));
                        }
                        else if (month.isEqual(finalStartDate) || month.isEqual(finalEndYear)) {
                            animalPriceSeries.put(month, Double.parseDouble(stock.getClose()));
                        }
                    }
            );
            priceSeries.put(animal, animalPriceSeries);

            try {
                double startPrice = Double.parseDouble(monthlyStockData.getMonthlyTimeSeries().get(startYear).getClose());
                double endPrice = Double.parseDouble(monthlyStockData.getMonthlyTimeSeries().get(endYear).getClose());

                double percentIncrease = (endPrice - startPrice) / startPrice;

                this.analyses.put(animal, Math.ceil(percentIncrease * 100.0) / 100.0);
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
                System.out.println(e.getMessage());
            }
        }
        this.bestIndicator = bestChineseAnimal;
        this.worstIndicator = worstChineseAnimal;
        this.bestStat = Math.ceil(bestPercentIncrease * 100.0) / 100.0;
        this.worstStat = Math.ceil(worstPercentIncrease * 100.0) / 100.0;
    }

    private Map<Integer, ChineseAnimals> initializeAnimalDates() {
        Map<Integer, ChineseAnimals> animalDates = new LinkedHashMap<>();
        LocalDate currentDate = LocalDate.now();
        LocalDate startDate = currentDate.minusYears(12);
        while (startDate.isBefore(currentDate)) {
            animalDates.put(startDate.getYear(), chineseNewYears.getChineseZodiac(startDate.getYear()));
            startDate = startDate.plusYears(1);
        }
        return animalDates;
    }

    public Map<Integer, ChineseAnimals> getAnimalDates() {
        return animalDates;
    }

    public Map<ChineseAnimals, Map<LocalDate, Double>> getPriceSeries() {
        return this.priceSeries;
    }

}
