package org.farmingdale.stockdiviner.model.alphavantage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class ImplAlphaVantageAPI implements AlphaVantageAPI {
    private static final String ALPHA_VANTAGE_URL = "https://alpha-vantage.p.rapidapi.com";
    private static final String apiKey = System.getProperty("alphaVantage.apiKey");
    private final OkHttpClient client;

    public ImplAlphaVantageAPI() {
        this.client = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request request = chain.request().newBuilder()
                            .addHeader("x-rapidapi-key", apiKey)
                            .addHeader("x-rapidapi-host", "alpha-vantage.p.rapidapi.com")
                            .build();

                    System.out.println(request.url());
                    return chain.proceed(request);
                })
                .build();
    }

    @Override
    public MonthlyStockData getMonthlyTimeSeries(String symbol) throws IOException {
        String url = ALPHA_VANTAGE_URL + "/query?function=TIME_SERIES_MONTHLY&symbol=" + symbol + "&apikey=" + apiKey;

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(MonthlyStockData.class, new StockDataDeserializer())
                    .registerTypeAdapter(MonthlyStockData.MetaData.class, new MetaDataDeserializer())
                    .registerTypeAdapter(MonthlyStockData.MonthlyTimeSeries.class, new MonthlyTimeSeriesDeserializer())
                    .create();

            return gson.fromJson(response.body().charStream(), MonthlyStockData.class);
        } catch (JsonSyntaxException e) {
            throw new IOException("Error parsing JSON", e);
        }
    }
}