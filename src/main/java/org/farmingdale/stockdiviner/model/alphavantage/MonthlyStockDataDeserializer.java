package org.farmingdale.stockdiviner.model.alphavantage;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class MonthlyStockDataDeserializer implements JsonDeserializer<MonthlyStockData> {
    @Override
    public MonthlyStockData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();


        MonthlyStockData monthlyStockData = new MonthlyStockData();
        Map<String, MonthlyStockData.MonthlyTimeSeries> monthlyTimeSeries = new HashMap<>();

        JsonObject metaDataObject = jsonObject.getAsJsonObject("Meta Data");
        MonthlyStockData.MetaData metaData = context.deserialize(metaDataObject, MonthlyStockData.MetaData.class);
        monthlyStockData.setMetaData(metaData);

        JsonObject timeSeriesObject = jsonObject.getAsJsonObject("Monthly Time Series");

        for (Map.Entry<String, JsonElement> entry : timeSeriesObject.entrySet()) {
            MonthlyStockData.MonthlyTimeSeries dataPoint = context.deserialize(entry.getValue(), MonthlyStockData.MonthlyTimeSeries.class);
            monthlyTimeSeries.put(entry.getKey(), dataPoint);
        }

        monthlyStockData.setMonthlyTimeSeries(monthlyTimeSeries);
        return monthlyStockData;
    }
}