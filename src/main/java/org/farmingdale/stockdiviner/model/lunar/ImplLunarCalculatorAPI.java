package org.farmingdale.stockdiviner.model.lunar;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ImplLunarCalculatorAPI implements LunarCalculatorAPI {
    private static final String LUNAR_CALCULATOR_URL = "https://aa.usno.navy.mil";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-dd");
    private final OkHttpClient client;

    public ImplLunarCalculatorAPI() {
        this.client = new OkHttpClient();
    }

    @Override
    public LunarPhase getLunarPhase(LocalDate date) throws IOException {
        String formattedDate = date.format(formatter);
        HttpUrl base = HttpUrl.parse(LUNAR_CALCULATOR_URL);
        HttpUrl url = base.newBuilder()
                .addPathSegment("api")
                .addPathSegment("moon")
                .addPathSegment("phases")
                .addPathSegment("date")
                .addQueryParameter("date", formattedDate)
                .addQueryParameter("nump", "1")
                .build();
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(PhaseData.class, new PhaseDataAdapter())
                    .create();

            JsonObject jsonObject = gson.fromJson(response.body().charStream(), JsonObject.class);
            JsonArray phasedataArray = jsonObject.getAsJsonArray("phasedata");
            Type listType = new TypeToken<ArrayList<PhaseData>>(){}.getType();
            List<PhaseData> phasedataList = gson.fromJson(phasedataArray, listType);

            PhaseData phaseData = phasedataList.get(0);
            return LunarPhase.valueOf(phaseData.getPhase().toUpperCase().replace(" ", "_"));
        } catch (JsonSyntaxException e) {
            throw new IOException("Error parsing JSON", e);
        }
    }
}
