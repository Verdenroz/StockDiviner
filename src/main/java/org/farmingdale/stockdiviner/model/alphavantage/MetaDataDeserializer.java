package org.farmingdale.stockdiviner.model.alphavantage;

import com.google.gson.*;

import java.lang.reflect.Type;

public class MetaDataDeserializer implements JsonDeserializer<MonthlyStockData.MetaData> {
    @Override
    public MonthlyStockData.MetaData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        MonthlyStockData.MetaData metaData = new MonthlyStockData.MetaData();
        metaData.setInformation(jsonObject.get("1. Information").getAsString());
        metaData.setSymbol(jsonObject.get("2. Symbol").getAsString());
        metaData.setLastRefreshed(jsonObject.get("3. Last Refreshed").getAsString());
        metaData.setTimeZone(jsonObject.get("4. Time Zone").getAsString());

        return metaData;
    }
}
