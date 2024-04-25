package org.farmingdale.stockdiviner;

import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.chart.NumberAxis;
import org.farmingdale.stockdiviner.model.Indicator;
import org.farmingdale.stockdiviner.model.alphavantage.AlphaVantageAPI;
import org.farmingdale.stockdiviner.model.alphavantage.ImplAlphaVantageAPI;
import org.farmingdale.stockdiviner.model.alphavantage.MonthlyStockData;
import org.farmingdale.stockdiviner.model.alphavantage.WeeklyStockData;
import org.farmingdale.stockdiviner.SearchController;
import org.farmingdale.stockdiviner.model.analysis.AnimalAnalysis;
import org.farmingdale.stockdiviner.model.analysis.LunarAnalysis;
import org.farmingdale.stockdiviner.model.analysis.ZodiacAnalysis;
import org.farmingdale.stockdiviner.model.zodiac.ZodiacSigns;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class InfoScreenController {
    String weekRange52 = "0 - 0";
    String volume = "0";
    String avgVolume = "1";
    String marketCap = "2";
    String peRatio = "3";
    String eps = "4";

    @FXML
    public Label weekRangeValue;
    @FXML
    public Label volumeValue;
    @FXML
    public Label avgVolumeValue;
    @FXML
    public Label marketCapValue;
    @FXML
    public Label PERatioValue;
    @FXML
    public Label EPSValue;
    @FXML
    public LineChart<String, Double> priceChart;
    @FXML
    public NumberAxis yaxis;
    public void initialize() throws Exception { // get actual api data
        SharedService sharedService = SharedService.getInstance();
        String symbol = sharedService.getData().getSymbol();
//        String symbol = "aapl";
        String stockName = sharedService.getData().getName();

        AlphaVantageAPI api = ImplAlphaVantageAPI.getInstance();
        Map<LocalDate, WeeklyStockData.WeeklyTimeSeries> weeklyTimeSeries = api.getWeeklyTimeSeries(symbol).getWeeklyTimeSeries();

        List<Map.Entry<LocalDate, WeeklyStockData.WeeklyTimeSeries>> listWeeklyTimeSeries = weeklyTimeSeries.entrySet().stream().sorted(Map.Entry.comparingByKey()).toList();
//        Double max = 0.0;

//        System.out.println("//////////////////////////////////////");
////        System.out.println(max);
//        System.out.println("///////////////////////////////////////");

        XYChart.Series<String, Double> s = maximumData(listWeeklyTimeSeries);

        yaxis.setAutoRanging(true);
        yaxis.setLowerBound(Double.parseDouble(listWeeklyTimeSeries.get(0).getValue().getClose()));
//        yaxis.setUpperBound(max);

        ZodiacAnalysis zodiacAnalysis = new ZodiacAnalysis(symbol);
        AnimalAnalysis animalAnalysis = new AnimalAnalysis(symbol);
        LunarAnalysis lunarAnalysis = new LunarAnalysis(symbol);

        System.out.println("---------------------------------------------------------------------------------------------");
        for (Map.Entry<Indicator, Double> entry : zodiacAnalysis.getAnalyses().entrySet()) {
            System.out.printf("star sign %s, Double? %f\n", entry.getKey(), entry.getValue());
        }
        System.out.println("---------------------------------------------------------------------------------------------");
        for (Map.Entry<Indicator, Double> entry : animalAnalysis.getAnalyses().entrySet()) {
            System.out.printf("animal %s, Double? %f\n", entry.getKey(), entry.getValue());
        }
        System.out.println("---------------------------------------------------------------------------------------------");
        for (Map.Entry<Indicator, Double> entry : lunarAnalysis.getAnalyses().entrySet()) {
            System.out.printf("phase %s, Double? %f\n", entry.getKey(), entry.getValue());
        }

        weekRangeValue.setText(weekRange52);
        volumeValue.setText(volume);
        avgVolumeValue.setText(avgVolume);
        marketCapValue.setText(marketCap);
        PERatioValue.setText(peRatio);
        EPSValue.setText(eps);

        priceChart.setLegendVisible(false);
        priceChart.getData().add(s);
        priceChart.setCreateSymbols(false);
        priceChart.setTitle(stockName);
//        Date date = new GregorianCalendar(2024, Calendar.JANUARY, 1).getTime();

//        XYChart.Series<LocalDate, Double> s = new XYChart.Series<>();

//        s.getData().add(new XYChart.Data<>(date, 1.0));

//        XYChart.Series<String, Double> s1 = new XYChart.Series<>();
//        s1.getData().add(new XYChart.Data<>("1/1/2024", 1.0));
//        s1.getData().add(new XYChart.Data<>("2/1/2024", 2.0));
//        s1.getData().add(new XYChart.Data<>("3/1/2024", 0.5));
//        s1.getData().add(new XYChart.Data<>("4/1/2024", 4.0));

//        priceChart.getData().add(s1);
    }

    private Double max(List<Map.Entry<LocalDate, WeeklyStockData.WeeklyTimeSeries>> timeSeries) {
        Double max = 0.0;
        Double curr;
        for (Map.Entry<LocalDate, WeeklyStockData.WeeklyTimeSeries> x: timeSeries) {
            curr = Double.parseDouble(x.getValue().getClose());
            if (max < curr) {
                max = curr;
            }
        }
        return max;
    }
    private Double min(List<Map.Entry<LocalDate, WeeklyStockData.WeeklyTimeSeries>> timeSeries) {
        Double min = 999999999.9;
        Double curr;
        for (Map.Entry<LocalDate, WeeklyStockData.WeeklyTimeSeries> x: timeSeries) {
            curr = Double.parseDouble(x.getValue().getClose());
            if (curr < min) {
                min = curr;
            }
        }
        return min;
    }
    private XYChart.Series<String, Double> maximumData(List<Map.Entry<LocalDate, WeeklyStockData.WeeklyTimeSeries>> listWeeklyTimeSeries) {
        //1 yr

        XYChart.Series<String, Double> series = new XYChart.Series<>();
        for (int i = 0; i < listWeeklyTimeSeries.size(); i++) {
            String date = listWeeklyTimeSeries.get(i).getKey().toString();
            Double price = Double.parseDouble(listWeeklyTimeSeries.get(i).getValue().getClose());

            series.getData().add(new XYChart.Data<>(date, price));
            System.out.println(date + ", " + price);
        }
        return series;
    }
    ChangeView changeView = ChangeView.getInstance();

    public void onLogOutButtonClicked(ActionEvent event) throws IOException {
        changeView.logout(event);
    }
//    private XYChart.Series<String, Double> FiftyTwoWeek(List<Map.Entry<LocalDate, WeeklyStockData.WeeklyTimeSeries>> listWeeklyTimeSeries) {
//        //1 yr
//
//        XYChart.Series<String, Double> series = new XYChart.Series<>();
//        for (int i = listWeeklyTimeSeries.size() - 52; i < listWeeklyTimeSeries.size(); i++) {
//            String date = listWeeklyTimeSeries.get(i).getKey().toString();
//            Double price = Double.parseDouble(listWeeklyTimeSeries.get(i).getValue().getClose());
//
//            series.getData().add(new XYChart.Data<>(date, price));
//            System.out.println(date + ", " + price);
//        }
//        return series;
//    }
//    private XYChart.Series<String, Double> FiveYear(List<Map.Entry<LocalDate, WeeklyStockData.WeeklyTimeSeries>> listWeeklyTimeSeries) {
//        //1 yr
//        XYChart.Series<String, Double> series = new XYChart.Series<>();
//        for (int i = listWeeklyTimeSeries.size() - 260; i < listWeeklyTimeSeries.size(); i++) {
//            String date = listWeeklyTimeSeries.get(i).getKey().toString();
//            Double price = Double.parseDouble(listWeeklyTimeSeries.get(i).getValue().getClose());
//
//            series.getData().add(new XYChart.Data<>(date, price));
//            System.out.println(date + ", " + price);
//        }
//        return series;
//    }
//    private XYChart.Series<String, Double> SixMonth(List<Map.Entry<LocalDate, WeeklyStockData.WeeklyTimeSeries>> listWeeklyTimeSeries) {
//        //1 yr
//        XYChart.Series<String, Double> series = new XYChart.Series<>();
//        for (int i = listWeeklyTimeSeries.size() - 24; i < listWeeklyTimeSeries.size(); i++) {
//            String date = listWeeklyTimeSeries.get(i).getKey().toString();
//            Double price = Double.parseDouble(listWeeklyTimeSeries.get(i).getValue().getClose());
//
//            series.getData().add(new XYChart.Data<>(date, price));
//            System.out.println(date + ", " + price);
//        }
//        return series;
//    }
//    private XYChart.Series<String, Double> OneMonth(List<Map.Entry<LocalDate, WeeklyStockData.WeeklyTimeSeries>> listWeeklyTimeSeries) {
//        //1 yr
//        XYChart.Series<String, Double> series = new XYChart.Series<>();
//        for (int i = listWeeklyTimeSeries.size() - 4; i < listWeeklyTimeSeries.size(); i++) {
//            String date = listWeeklyTimeSeries.get(i).getKey().toString();
//            Double price = Double.parseDouble(listWeeklyTimeSeries.get(i).getValue().getClose());
//
//            series.getData().add(new XYChart.Data<>(date, price));
//            System.out.println(date + ", " + price);
//        }
//        return series;
//    }

}

