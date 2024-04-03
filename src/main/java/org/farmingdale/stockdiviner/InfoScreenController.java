package org.farmingdale.stockdiviner;

import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.chart.NumberAxis;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class InfoScreenController {
    String weekRange52 = "0 - 0";
    String volume = "0";
    String avgVolume = "1";
    String marketCap = "2";
    String peRatio = "3";
    String eps = "4";
    String stockName = "Tesla";
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
    public void initialize() { // get actual api data
        weekRangeValue.setText(weekRange52);
        volumeValue.setText(volume);
        avgVolumeValue.setText(avgVolume);
        marketCapValue.setText(marketCap);
        PERatioValue.setText(peRatio);
        EPSValue.setText(eps);

        priceChart.setTitle(stockName);
        Date date = new GregorianCalendar(2024, Calendar.JANUARY, 1).getTime();

        XYChart.Series<Date, Double> s = new XYChart.Series<>();

        s.getData().add(new XYChart.Data<>(date, 1.0));

        XYChart.Series<String, Double> s1 = new XYChart.Series<>();
        s1.getData().add(new XYChart.Data<>("1/1/2024", 1.0));
        s1.getData().add(new XYChart.Data<>("2/1/2024", 2.0));
        s1.getData().add(new XYChart.Data<>("3/1/2024", 0.5));
        s1.getData().add(new XYChart.Data<>("4/1/2024", 4.0));

        priceChart.getData().add(s1);
    }
}
