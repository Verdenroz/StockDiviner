package org.farmingdale.stockdiviner.model;

import com.google.gson.*;

import java.lang.reflect.Type;

public class MonthlyTimeSeriesDeserializer implements JsonDeserializer<StockData.MonthlyTimeSeries> {
    @Override
    public StockData.MonthlyTimeSeries deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        StockData.MonthlyTimeSeries monthlyTimeSeries = new StockData.MonthlyTimeSeries();
        monthlyTimeSeries.setOpen(jsonObject.get("1. open").getAsString());
        monthlyTimeSeries.setHigh(jsonObject.get("2. high").getAsString());
        monthlyTimeSeries.setLow(jsonObject.get("3. low").getAsString());
        monthlyTimeSeries.setClose(jsonObject.get("4. close").getAsString());
        monthlyTimeSeries.setVolume(jsonObject.get("5. volume").getAsString());

        return monthlyTimeSeries;
    }
}
