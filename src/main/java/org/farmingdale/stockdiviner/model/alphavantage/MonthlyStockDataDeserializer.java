package org.farmingdale.stockdiviner.model.alphavantage;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * Deserializer for the StockData class
 */
public class MonthlyStockDataDeserializer implements JsonDeserializer<MonthlyStockData> {
    @Override
    public MonthlyStockData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        MonthlyStockData monthlyStockData = new MonthlyStockData();
        Map<LocalDate, MonthlyStockData.MonthlyTimeSeries> monthlyTimeSeries = new HashMap<>();

        JsonObject timeSeriesObject = jsonObject.getAsJsonObject("Monthly Adjusted Time Series");

        for (Map.Entry<String, JsonElement> entry : timeSeriesObject.entrySet()) {
            MonthlyStockData.MonthlyTimeSeries dataPoint = context.deserialize(entry.getValue(), MonthlyStockData.MonthlyTimeSeries.class);
            LocalDate date = LocalDate.parse(entry.getKey(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            monthlyTimeSeries.put(date, dataPoint);
        }

        monthlyStockData.setMonthlyTimeSeries(monthlyTimeSeries);
        return monthlyStockData;
    }
}