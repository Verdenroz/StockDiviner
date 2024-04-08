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
public class StockDataDeserializer implements JsonDeserializer<StockData> {
    @Override
    public StockData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        StockData stockData = new StockData();
        Map<LocalDate, StockData.MonthlyTimeSeries> monthlyTimeSeries = new HashMap<>();

        JsonObject timeSeriesObject = jsonObject.getAsJsonObject("Monthly Time Series");

        for (Map.Entry<String, JsonElement> entry : timeSeriesObject.entrySet()) {
            StockData.MonthlyTimeSeries dataPoint = context.deserialize(entry.getValue(), StockData.MonthlyTimeSeries.class);
            LocalDate date = LocalDate.parse(entry.getKey(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            monthlyTimeSeries.put(date, dataPoint);
        }

        stockData.setMonthlyTimeSeries(monthlyTimeSeries);
        return stockData;
    }
}