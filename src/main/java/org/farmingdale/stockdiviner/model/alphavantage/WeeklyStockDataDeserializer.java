package org.farmingdale.stockdiviner.model.alphavantage;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class WeeklyStockDataDeserializer implements JsonDeserializer<WeeklyStockData> {
    @Override
    public WeeklyStockData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        WeeklyStockData weeklyStockData = new WeeklyStockData();
        Map<LocalDate, WeeklyStockData.WeeklyTimeSeries> weeklyTimeSeries = new HashMap<>();

        JsonObject timeSeriesObject = jsonObject.getAsJsonObject("Weekly Adjusted Time Series");

        for (Map.Entry<String, JsonElement> entry : timeSeriesObject.entrySet()) {
            WeeklyStockData.WeeklyTimeSeries dataPoint = context.deserialize(entry.getValue(), WeeklyStockData.WeeklyTimeSeries.class);
            LocalDate date = LocalDate.parse(entry.getKey(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            weeklyTimeSeries.put(date, dataPoint);
        }

        weeklyStockData.setWeeklyTimeSeries(weeklyTimeSeries);
        return weeklyStockData;
    }
}
