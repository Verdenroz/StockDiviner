package org.farmingdale.stockdiviner.model.financialmodeling;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Deserializes a list of FullQuoteData objects from JSON
 */
public class ListFullQuoteDataDeserializer implements JsonDeserializer<List<FullQuoteData>> {
    @Override
    public List<FullQuoteData> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonArray jsonArray = json.getAsJsonArray();
        List<FullQuoteData> fullQuoteDataList = new ArrayList<>();

        for (JsonElement jsonElement : jsonArray) {
            FullQuoteData fullQuoteData = context.deserialize(jsonElement, FullQuoteData.class);
            fullQuoteDataList.add(fullQuoteData);
        }

        return fullQuoteDataList;
    }
}