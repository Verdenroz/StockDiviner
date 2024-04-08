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
import java.util.*;
import java.util.stream.Collectors;

public class ImplLunarCalculatorAPI implements LunarCalculatorAPI {
    private static volatile LunarCalculatorAPI instance;
    private static final String LUNAR_CALCULATOR_URL = "https://aa.usno.navy.mil";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-dd");
    private final OkHttpClient client;

    private ImplLunarCalculatorAPI() {
        this.client = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request request = chain.request().newBuilder()
                            .build();

                    System.out.println(request.url());
                    return chain.proceed(request);
                })
                .build();
    }

    /**
     * Returns the instance of the LunarCalculatorAPI
     */
    public static LunarCalculatorAPI getInstance() {
        if (instance == null) {
            synchronized (ImplLunarCalculatorAPI.class) {
                if (instance == null) {
                    instance = new ImplLunarCalculatorAPI();
                }
            }
        }
        return instance;
    }

    @Override
    public Map<LocalDate, LunarPhase> getLunarPhase(LocalDate date) throws IOException {
        String formattedDate = date.format(formatter);
        HttpUrl base = HttpUrl.parse(LUNAR_CALCULATOR_URL);
        HttpUrl url = base.newBuilder()
                .addPathSegment("api")
                .addPathSegment("moon")
                .addPathSegment("phases")
                .addPathSegment("date")
                .addQueryParameter("date", formattedDate)
                .addQueryParameter("nump", "99")
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
            Type listType = new TypeToken<ArrayList<PhaseData>>() {
            }.getType();
            List<PhaseData> phasedataList = gson.fromJson(phasedataArray, listType);

            Map<LocalDate, LunarPhase> lunarPhases = new HashMap<>();

            for (PhaseData phaseData : phasedataList) {
                LocalDate phaseDate = LocalDate.of(phaseData.getYear(), phaseData.getMonth(), phaseData.getDay());

                LunarPhase lunarPhase = LunarPhase.valueOf(phaseData.getPhase().toUpperCase().replace(" ", "_"));

                lunarPhases.put(phaseDate, lunarPhase);
            }
            // Sort the lunarPhases map by keys (dates) in descending order
            return lunarPhases.entrySet().stream()
                    .sorted(Map.Entry.<LocalDate, LunarPhase>comparingByKey().reversed())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        } catch (JsonSyntaxException e) {
            throw new IOException("Error parsing JSON", e);
        }
    }
}
