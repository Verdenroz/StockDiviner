package org.farmingdale.stockdiviner.model.alphavantage;

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * Deserializer for the MonthlyTimeSeries class
 * Example JSON:
 * 	"2024-02-21": {
 * 		"1. open": "393.9400",
 * 		"2. high": "488.6200",
 * 		"3. low": "393.0500",
 * 		"4. close": "468.0300",
 * 		"5. volume": "372857673"
 * 		       },
 */
public class MonthlyTimeSeriesDeserializer implements JsonDeserializer<MonthlyStockData.MonthlyTimeSeries> {
    @Override
    public MonthlyStockData.MonthlyTimeSeries deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        MonthlyStockData.MonthlyTimeSeries monthlyTimeSeries = new MonthlyStockData.MonthlyTimeSeries();
        monthlyTimeSeries.setClose(jsonObject.get("5. adjusted close").getAsString());

        return monthlyTimeSeries;
    }
}
