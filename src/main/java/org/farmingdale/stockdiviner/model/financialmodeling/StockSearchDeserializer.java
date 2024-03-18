package org.farmingdale.stockdiviner.model.financialmodeling;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class StockSearchDeserializer implements JsonDeserializer<List<StockSearch>>{
    @Override
    public List<StockSearch> deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonArray jsonArray = jsonElement.getAsJsonArray();
        List<StockSearch> stockSearchList = new ArrayList<>();

        for (JsonElement element : jsonArray) {
            StockSearch stockSearch = jsonDeserializationContext.deserialize(element, StockSearch.class);
            stockSearchList.add(stockSearch);
        }

        return stockSearchList;
    }
}
