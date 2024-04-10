package org.farmingdale.stockdiviner.model.alphavantage;

import com.google.gson.*;

import java.lang.reflect.Type;

public class WeeklyTimeSeriesDeserializer implements JsonDeserializer<WeeklyStockData.WeeklyTimeSeries> {
    @Override
    public WeeklyStockData.WeeklyTimeSeries deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        WeeklyStockData.WeeklyTimeSeries weeklyTimeSeries = new WeeklyStockData.WeeklyTimeSeries();
        weeklyTimeSeries.setClose(jsonObject.get("4. close").getAsString());

        return weeklyTimeSeries;
    }
}
