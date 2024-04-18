package org.farmingdale.stockdiviner.model.financialmodeling;

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * Deserializes a single FullQuoteData object from JSON

 */
public class FullQuoteDataDeserializer implements JsonDeserializer<FullQuoteData> {
    @Override
    public FullQuoteData deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        FullQuoteData fullQuoteData = new FullQuoteData();

        fullQuoteData.setSymbol(jsonObject.get("symbol").getAsString());
        fullQuoteData.setName(jsonObject.get("name").getAsString());
        fullQuoteData.setPrice(jsonObject.get("price").getAsDouble());
        fullQuoteData.setChangesPercentage(jsonObject.get("changesPercentage").getAsDouble());
        fullQuoteData.setChange(jsonObject.get("change").getAsDouble());
        fullQuoteData.setDayLow(jsonObject.get("dayLow").getAsDouble());
        fullQuoteData.setDayHigh(jsonObject.get("dayHigh").getAsDouble());
        fullQuoteData.setYearHigh(jsonObject.get("yearHigh").getAsDouble());
        fullQuoteData.setYearLow(jsonObject.get("yearLow").getAsDouble());
        fullQuoteData.setMarketCap(jsonObject.get("marketCap").getAsLong());
        fullQuoteData.setPriceAvg50(jsonObject.get("priceAvg50").getAsDouble());
        fullQuoteData.setPriceAvg200(jsonObject.get("priceAvg200").getAsDouble());
        fullQuoteData.setExchange(jsonObject.get("exchange").getAsString());
        fullQuoteData.setVolume(jsonObject.get("volume").getAsLong());
        fullQuoteData.setAvgVolume(jsonObject.get("avgVolume").getAsLong());
        fullQuoteData.setOpen(jsonObject.get("open").getAsDouble());
        fullQuoteData.setPreviousClose(jsonObject.get("previousClose").getAsDouble());
        fullQuoteData.setSharesOutstanding(jsonObject.get("sharesOutstanding").getAsLong());

        return fullQuoteData;
    }
}
