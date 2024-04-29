package org.farmingdale.stockdiviner.model.lunar;

import com.google.gson.*;

import java.lang.reflect.Type;

public class PhaseDataAdapter implements JsonDeserializer<PhaseData> {
    @Override
    public PhaseData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        PhaseData phaseData = new PhaseData();
        phaseData.setPhase(jsonObject.get("phase").getAsString());
        phaseData.setDay(jsonObject.get("day").getAsInt());
        phaseData.setMonth(jsonObject.get("month").getAsInt());
        phaseData.setYear(jsonObject.get("year").getAsInt());
        return phaseData;
    }
}