package org.farmingdale.stockdiviner.model.lunar;

import com.google.gson.*;

import java.lang.reflect.Type;

public class PhaseDataAdapter implements JsonSerializer<PhaseData>, JsonDeserializer<PhaseData> {
    @Override
    public JsonElement serialize(PhaseData src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("phase", src.getPhase());
        return jsonObject;
    }

    @Override
    public PhaseData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        PhaseData phaseData = new PhaseData();
        phaseData.setPhase(jsonObject.get("phase").getAsString());
        return phaseData;
    }
}