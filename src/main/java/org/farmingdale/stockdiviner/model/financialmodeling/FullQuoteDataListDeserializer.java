package org.farmingdale.stockdiviner.model.financialmodeling;


import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FullQuoteDataListDeserializer implements JsonDeserializer<List<FullQuoteData>> {
    @Override
    public List<FullQuoteData> deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonArray jsonArray = json.getAsJsonArray();

        List<FullQuoteData> fullQuoteDataList = new ArrayList<>();
        Gson gson = new Gson();

        for (JsonElement jsonElement : jsonArray) {
            FullQuoteData fullQuoteData = gson.fromJson(jsonElement, FullQuoteData.class);
            fullQuoteDataList.add(fullQuoteData);
        }

        return fullQuoteDataList;
    }
}
