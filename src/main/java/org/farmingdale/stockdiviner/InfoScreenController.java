package org.farmingdale.stockdiviner;

import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.RadioButton;
import javafx.util.Duration;
import org.farmingdale.stockdiviner.model.Indicator;
import org.farmingdale.stockdiviner.model.alphavantage.AlphaVantageAPI;
import org.farmingdale.stockdiviner.model.alphavantage.ImplAlphaVantageAPI;
import org.farmingdale.stockdiviner.model.alphavantage.MonthlyStockData;
import org.farmingdale.stockdiviner.model.alphavantage.WeeklyStockData;
import org.farmingdale.stockdiviner.SearchController;
import org.farmingdale.stockdiviner.model.analysis.AnimalAnalysis;
import org.farmingdale.stockdiviner.model.analysis.LunarAnalysis;
import org.farmingdale.stockdiviner.model.analysis.ZodiacAnalysis;
import org.farmingdale.stockdiviner.model.animals.ChineseAnimals;
import org.farmingdale.stockdiviner.model.lunar.LunarPhase;
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
    @FXML
    public ComboBox<Indicator> charts;
    private ZodiacAnalysis zodiacAnalysis;
    private AnimalAnalysis animalAnalysis;
    private LunarAnalysis lunarAnalysis;
    private AlphaVantageAPI api;
    private String symbol;
    private Map<LocalDate, WeeklyStockData.WeeklyTimeSeries> weeklyTimeSeries;
    @FXML
    public RadioButton zodiacRadioButton;
    @FXML
    public RadioButton lunarRadioButton;
    @FXML
    public RadioButton animalRadioButton;
    public void initialize() throws Exception { // get actual api data
        SharedService sharedService = SharedService.getInstance();
        symbol = sharedService.getData().getSymbol();
        String stockName = sharedService.getData().getName();

        api = ImplAlphaVantageAPI.getInstance();
        weeklyTimeSeries = api.getWeeklyTimeSeries(symbol).getWeeklyTimeSeries();

        List<Map.Entry<LocalDate, WeeklyStockData.WeeklyTimeSeries>> listWeeklyTimeSeries = weeklyTimeSeries.entrySet().stream().sorted(Map.Entry.comparingByKey()).toList();

        XYChart.Series<String, Double> s = maximumData(listWeeklyTimeSeries);

        yaxis.setAutoRanging(true);
        yaxis.setLowerBound(Double.parseDouble(listWeeklyTimeSeries.get(0).getValue().getClose()));

        zodiacAnalysis = new ZodiacAnalysis(symbol);
        animalAnalysis = new AnimalAnalysis(symbol);
        lunarAnalysis = new LunarAnalysis(symbol);

        Map<ZodiacSigns, Map<LocalDate, Double>> zodiacPriceSeries = zodiacAnalysis.getPriceSeries();

        charts.getItems().addAll(zodiacPriceSeries.keySet());

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
        priceChart.setAnimated(false);
    }

    public void handleZodiacRadioButton(ActionEvent event) throws IOException {
        priceChart.getData().clear();
        charts.getItems().clear();
        Map<ZodiacSigns, Map<LocalDate, Double>> zodiacPriceSeries = zodiacAnalysis.getPriceSeries();
        charts.getItems().addAll(zodiacPriceSeries.keySet());
        //charts.setValue(ZodiacSigns.AQUARIUS);

        List<Map.Entry<LocalDate, WeeklyStockData.WeeklyTimeSeries>> listWeeklyTimeSeries = weeklyTimeSeries.entrySet().stream().sorted(Map.Entry.comparingByKey()).toList();

        XYChart.Series<String, Double> s = maximumData(listWeeklyTimeSeries);
        priceChart.getData().add(s);
    }
    public void handleAnimalRadioButton(ActionEvent event) throws IOException {
        priceChart.getData().clear();
        charts.getItems().clear();
        Map<ChineseAnimals, Map<LocalDate, Double>> animalPriceSeries = animalAnalysis.getPriceSeries();
        charts.getItems().addAll(animalPriceSeries.keySet());
        //charts.setValue(ChineseAnimals.RAT);

        List<Map.Entry<LocalDate, WeeklyStockData.WeeklyTimeSeries>> listWeeklyTimeSeries = weeklyTimeSeries.entrySet().stream().sorted(Map.Entry.comparingByKey()).toList();

        XYChart.Series<String, Double> s = maximumData(listWeeklyTimeSeries);
        priceChart.getData().add(s);
    }
    public void handleMoonRadioButton(ActionEvent event) throws IOException {
        priceChart.getData().clear();
        charts.getItems().clear();
        Map<LunarPhase, Map<LocalDate, Double>> lunarPriceSeries = lunarAnalysis.getPriceSeries();
        charts.getItems().addAll(lunarPriceSeries.keySet());
        //charts.setValue(LunarPhase.FULL_MOON);

        List<Map.Entry<LocalDate, WeeklyStockData.WeeklyTimeSeries>> listWeeklyTimeSeries = weeklyTimeSeries.entrySet().stream().sorted(Map.Entry.comparingByKey()).toList();

        XYChart.Series<String, Double> s = maximumData(listWeeklyTimeSeries);
        priceChart.getData().add(s);
    }

    public void handleChartSelect(ActionEvent event) {
        priceChart.getData().clear();
        Indicator indicator = charts.getValue();
        Map<LocalDate, Double> selectedSeries = null;


        if (zodiacRadioButton.isSelected()) {
            XYChart.Series<String, Double> s1 = new XYChart.Series<>();
            selectedSeries = zodiacAnalysis.getPriceSeries().get(indicator);
            for (Map.Entry<LocalDate, Double> entry: selectedSeries.entrySet()) {
                s1.getData().add(new XYChart.Data<>(entry.getKey().toString(), entry.getValue()));
            }
            priceChart.getData().add(s1);
        } else if (animalRadioButton.isSelected()) {
            XYChart.Series<String, Double> s2 = new XYChart.Series<>();
            selectedSeries = animalAnalysis.getPriceSeries().get(indicator);
            for (Map.Entry<LocalDate, Double> entry: selectedSeries.entrySet()) {
                s2.getData().add(new XYChart.Data<>(entry.getKey().toString(), entry.getValue()));
            }
            priceChart.getData().add(s2);
        } else if (lunarRadioButton.isSelected()) {
            XYChart.Series<String, Double> s3 = new XYChart.Series<>();
            selectedSeries = lunarAnalysis.getPriceSeries().get(indicator);
            for (Map.Entry<LocalDate, Double> entry: selectedSeries.entrySet()) {
                s3.getData().add(new XYChart.Data<>(entry.getKey().toString(), entry.getValue()));
            }
            priceChart.getData().add(s3);
        }

//        XYChart.Series<String, Double> s1 = new XYChart.Series<>();
//        s1.getData().add(new XYChart.Data<>("1/1/2024", 1.0));
//        s1.getData().add(new XYChart.Data<>("2/1/2024", 2.0));
//        s1.getData().add(new XYChart.Data<>("3/1/2024", 0.5));
//        s1.getData().add(new XYChart.Data<>("4/1/2024", 4.0));
//
//        priceChart.getData().add(s1);
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

    private final PauseTransition pause = new PauseTransition(Duration.seconds(0.25));

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

