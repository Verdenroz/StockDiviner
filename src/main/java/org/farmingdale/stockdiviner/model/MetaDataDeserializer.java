package org.farmingdale.stockdiviner.model;

import com.google.gson.*;

import java.lang.reflect.Type;

public class MetaDataDeserializer implements JsonDeserializer<StockData.MetaData> {
    @Override
    public StockData.MetaData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        StockData.MetaData metaData = new StockData.MetaData();
        metaData.setInformation(jsonObject.get("1. Information").getAsString());
        metaData.setSymbol(jsonObject.get("2. Symbol").getAsString());
        metaData.setLastRefreshed(jsonObject.get("3. Last Refreshed").getAsString());
        metaData.setTimeZone(jsonObject.get("4. Time Zone").getAsString());

        return metaData;
    }
}
