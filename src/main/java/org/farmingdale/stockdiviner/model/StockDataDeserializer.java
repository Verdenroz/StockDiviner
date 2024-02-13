package org.farmingdale.stockdiviner.model;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class StockDataDeserializer implements JsonDeserializer<StockData> {
    @Override
    public StockData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();


        StockData stockData = new StockData();
        Map<String, StockData.MonthlyTimeSeries> monthlyTimeSeries = new HashMap<>();

        JsonObject metaDataObject = jsonObject.getAsJsonObject("Meta Data");
        StockData.MetaData metaData = context.deserialize(metaDataObject, StockData.MetaData.class);
        stockData.setMetaData(metaData);

        JsonObject timeSeriesObject = jsonObject.getAsJsonObject("Monthly Time Series");

        for (Map.Entry<String, JsonElement> entry : timeSeriesObject.entrySet()) {
            StockData.MonthlyTimeSeries dataPoint = context.deserialize(entry.getValue(), StockData.MonthlyTimeSeries.class);
            monthlyTimeSeries.put(entry.getKey(), dataPoint);
        }

        stockData.setMonthlyTimeSeries(monthlyTimeSeries);
        return stockData;
    }
}